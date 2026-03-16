# Image Manipulator: Architecture, How It Works, and Productionization Guide

## 1) What this project is

This is a Java image processing app with three entry modes:
- **Batch script mode** (`-file`): executes commands from a script file.
- **Interactive text mode** (`-text`): executes commands entered in a REPL-like console.
- **GUI mode** (no args): launches a Swing interface with buttons for operations.

The design follows an MVC shape:
- **Model**: image state + image operations.
- **Controllers**: parse/route user actions to model operations.
- **Views**: text output or Swing UI rendering.

## 2) Runtime flow

### Program startup
`ImageProcessingProgram.main` creates one `ImageModelImpl` and then chooses a controller/view pair based on args.

- `-file`: reads a script file into a `Readable`, starts text controller.
- `-text`: reads from stdin, starts text controller.
- no args: starts Swing GUI view + GUI controller.

### Text mode flow
1. `ImageTextControllerImpl` initializes a command map from command string -> command object constructor.
2. In `start()`, it loops through tokens from `Scanner`.
3. It ignores comment lines beginning with `#`.
4. `q`/`quit` exits.
5. Known commands build an `ImageCommand` and call `execute(model)`.
6. Unknown/invalid command sequences are rendered as errors.

### GUI mode flow
1. `ImageGUIViewImpl` constructs Swing components (buttons, image panel, histogram panel).
2. `ImageGUIControllerImpl` registers as an `ActionListener`.
3. Clicking a button triggers `actionPerformed`, which dispatches to helpers (`brightenHelper`, `flipHelper`, etc.).
4. Operations are always performed on a single model key: `"guiImage"`.
5. After each successful op, `updateView()` refreshes displayed image + histogram.

## 3) Core domain model internals

## Image storage strategy
`ImageModelImpl` stores images in `HashMap<String, Image>`.

Benefits:
- Easy named checkpoints in text mode (`source`, `source-blur`, etc.).
- Constant-time lookup by logical name.

Tradeoff:
- In-memory only; no persistence or eviction strategy.

## Operation delegation
`ImageModelImpl` does orchestration and delegates pixel math to `ImageImpl`:
- component greyscale
- brighten/darken
- flips
- blur/sharpen (convolution)
- greyscale/sepia (3x3 color transform)
- downsize

This keeps controller logic thin and image math encapsulated.

## Pixel/image immutability pattern
`ImageImpl` fields are final (`width`, `height`, `maxValue`, `pixels`), and most ops create new pixel arrays and return new `ImageImpl` instances.

Notable detail: `getPixels()` deep-copies pixel values into new `RGBPixel` objects before returning, so callers don’t mutate internal arrays directly.

## 4) Supported operations and algorithm sketch

- **Greyscale component**: compute one scalar per pixel from channel/value/intensity/luma and write to R=G=B.
- **Brighten/darken**: add/subtract scalar per channel with clamping.
- **Flip**: swap pairs across horizontal or vertical axis.
- **Blur/sharpen**: convolution over each pixel with fixed kernel.
- **Color transformation**: multiply pixel RGB vector by 3x3 matrix.
- **Downsize**: maps new coordinates to old image; if non-integer, averages surrounding neighbors.

Complexity rough order:
- Per-pixel ops (brighten, component, transform): **O(W×H)**.
- Convolution (K×K kernel): **O(W×H×K²)**.
- Downsize: about **O(newW×newH)** with local neighborhood sampling.

## 5) File I/O architecture

The command layer chooses reader/writer by extension:
- `Load`/`Save` switch on last 3 chars (`ppm`, `png`, `jpg`, `bmp`).

I/O strategy classes:
- `AbstractImageInputOutput`: common `ImageIO`-based load/save (BMP/PNG/JPG).
- `PPMImageInputOutput`: custom ASCII P3 parser/writer.

This is a clean “strategy-like” structure, but extension detection can be made more robust (e.g., case-insensitive, full suffix parsing, MIME sniffing).

## 6) GUI subsystem behavior

`ImageGUIViewImpl` is straightforward Swing:
- Left column: load/save + manipulation buttons.
- Right side: image preview in scroll pane + histogram panel.

`HistogramImpl` computes 256-bin frequencies for red/green/blue/intensity and draws polylines.

## 7) Current strengths

- Good educational MVC separation.
- Command pattern for text mode is easy to extend.
- Clear operation library in model/image layer.
- Good test suite footprint in `test/`.

## 8) Current bottlenecks / risks for scaling

1. **Single-process, in-memory design**
   - No persistence, caching, or streaming.
   - Large images can stress heap.

2. **Synchronous execution**
   - Heavy ops run inline; no background jobs.
   - GUI may feel blocked on large images.

3. **Error handling consistency**
   - Some places throw; some print to stdout.
   - No typed error model or error codes.

4. **Extension-based format detection**
   - Last-3-char parsing is brittle (`jpeg`, uppercase, malformed names).

5. **Limited observability**
   - No structured logs, metrics, tracing, profiling hooks.

6. **No deployment boundary**
   - This is a desktop app architecture, not a service API architecture.

## 9) How to productionize (practical staged roadmap)

### Stage A: Hardening in current architecture
- Add a build tool (`Gradle` or `Maven`) and pin dependencies.
- Add CI: compile + unit tests + static analysis.
- Standardize exceptions and user-facing messages.
- Add image size guards + memory limits.
- Improve file type handling (`Path`, robust extension parsing).

### Stage B: Performance & correctness
- Replace exception-driven bounds handling in convolution with explicit boundary checks.
- Add benchmark tests (small/medium/large images).
- Introduce optional parallel processing for independent rows/tiles.
- Add undo stack for GUI and memory-aware eviction policy.

### Stage C: Service/API extraction
Split into modules:
1. `image-core` (pure operations, no UI)
2. `image-io` (formats + decoding)
3. `image-api` (REST/gRPC endpoints)
4. `image-worker` (async job execution)
5. `image-ui` (optional web/desktop client)

Then expose APIs like:
- `POST /images` upload
- `POST /images/{id}/operations` run one op
- `POST /pipelines` run multiple ops
- `GET /jobs/{id}` poll async job status
- `GET /images/{id}` download output

### Stage D: Cloud scale patterns
- **Storage**: object storage for originals/outputs.
- **Queue**: async jobs via message queue.
- **Workers**: autoscaled stateless processors.
- **DB**: metadata (job state, user ownership, audit trail).
- **CDN**: serve generated assets.
- **Caching**: deduplicate repeated transformations.

### Stage E: Security & multi-tenant readiness
- AuthN/AuthZ (JWT/OAuth).
- Rate limits + quotas.
- Malware scanning and file validation on upload.
- Strict resource ceilings per job.
- Full audit logging.

## 10) Concrete design recommendations if you were starting this as a startup product

- Keep this repo’s **operation logic** as the nucleus.
- Build a stateless processing microservice around it.
- Treat each transform request as an immutable job.
- Store images outside process memory and pass references.
- Make the UI a client, not the compute host.
- Add product features: operation history, presets, batch pipelines, webhooks.

## 11) “If I had 2 weeks” implementation plan

1. Add Maven + JUnit runner + CI pipeline.
2. Extract `model/image` into a reusable library module.
3. Add a minimal Spring Boot API with upload + apply-op + download.
4. Use local disk/S3-compatible storage abstraction.
5. Add async execution with a worker thread pool.
6. Add health endpoint + logs + basic Prometheus metrics.
7. Containerize with Docker and ship a compose stack.

That gives you a credible v1 production architecture while preserving your current logic.
