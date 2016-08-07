#version 330 core

in vec2 _texCoords;

out vec4 color;

uniform sampler2D sampler;

void main(void) {

    color = texture(sampler, _texCoords);

}