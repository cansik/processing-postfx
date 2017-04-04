package ch.bildspur.postfx;

import ch.bildspur.postfx.pass.Pass;
import processing.core.PGraphics;

/**
 * Handles the pass buffer and render technique.
 */
public interface Supervisor {

    /**
     * Reset the resolution of the pass buffer.
     *
     * @param width  New width of the pass buffer.
     * @param height New height of the pass buffer.
     */
    void setResolution(int width, int height);

    /**
     * Returns pass buffer resolution.
     *
     * @return Int array as shader uniform.
     */
    int[] getResolution();

    /**
     * Start a new multi-pass rendering.
     *
     * @param graphics Texture used as input.
     */
    void render(PGraphics graphics);

    /**
     * Apply pass to texture.
     *
     * @param pass Pass to apply.
     */
    void pass(Pass pass);

    /**
     * Compose and finalize rendering.
     *
     * @param graphics Texture to render onto.
     */
    void compose(PGraphics graphics);

    /**
     * Get next pass of the pass buffer.
     *
     * @return Next pass of the pass buffer.
     */
    PGraphics getNextPass();

    /**
     * Get current pass of the pass buffer.
     *
     * @return Current pass of the pass buffer.
     */
    PGraphics getCurrentPass();

    /**
     * Clear a pass (black with alpha).
     *
     * @param pass Pass to clear.
     */
    void clearPass(PGraphics pass);
}
