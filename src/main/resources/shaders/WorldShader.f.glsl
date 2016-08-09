#version 330 core

in vec2 _texCoords;
in vec3 _normal;

out vec4 color;

uniform sampler2D sampler;
uniform vec3 lightDir;
uniform float ambientLight;

void main(void) {

    //vec3 diffuse = texture(sampler, _texCoords).xyz;

    //float dotVal = max(dot(-lightDir, _normal), ambientLight);

    //color = vec4(diffuse * dotVal, 1.0);

    color = vec4(_normal, 1.0);

}