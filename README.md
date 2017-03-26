# Processing PostFX
A postFX library for processing.

The post fx library which currently contains following effects:

* bright pass
* blur horizontal
* blur vertical
* sobel edge detection
* toon filter

Some of the shaders are from the [spite/Wagner](https://github.com/spite/Wagner/) respoitory and only adapted to work in processing.

You can use it on every `PGraphics3D` (except `g`) object like this:

```java
PGraphics bloom = createGraphics(width, height, P2D);
PostFX fx = new PostFX(width, height);

// bloom filter
fx.filter(canvas)
    .brightPass(0.5)
    .blur(20, 50, false)
    .blur(20, 50, true)
    .close(bloom);
    
// draw the bloom onto the original image
blendMode(BLEND);
image(canvas, 0, 0);

blendMode(SCREEN);
image(bloom, 0, 0);
```
