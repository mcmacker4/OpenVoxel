#version 330 core

in vec2 _texCoords;
in vec3 _normal;

out vec4 color;

uniform sampler2D sampler;
uniform vec3 lightDir;

void main(void) {

    vec3 diffuse = texture(sampler, _texCoords).xyz;

    float dotVal = max(dot(-lightDir, _normal), 0.5);

    color = vec4(diffuse * dotVal, 1.0);

}