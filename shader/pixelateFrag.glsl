#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D texture;

uniform vec2 resolution;
uniform int level;

ivec2 pix;

// 4 sampe interpolation
vec4 tl, tr, bl, br;
int margin;

ivec2 corner(int x, int y) {
    return ivec2(min(max(ivec2(0, 0), pix.xy + ivec2(margin * x, margin * y)), resolution.xy - 1));
}

ivec2 imod(ivec2 vec, int modulus)
{
    return ivec2(mod(vec.x, modulus), mod(vec.y, modulus));
}

vec4 texelFetch(sampler2D tex, vec2 size, ivec2 coord)
{
    return texture2D(tex, vec2(float(coord.x) / size.x,
                               float(coord.y) / size.y));
}

void main() {
    pix.xy = ivec2(gl_FragCoord.xy);

    pix += (level / 2 - (imod(pix, level)));
    margin = level / 4;

    tl = texelFetch(texture, resolution, corner(1,1));
    tr = texelFetch(texture, resolution, corner(3,1));
    bl = texelFetch(texture, resolution, corner(1,3));
    br = texelFetch(texture, resolution, corner(3,3));

    gl_FragColor = (tl+tr+bl+br) * 0.25;
}