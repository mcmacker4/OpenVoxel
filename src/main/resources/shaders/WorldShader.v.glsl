#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoords;
layout (location = 2) in vec3 normal;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

out vec2 _texCoords;
out vec3 _normal;

void main(void) {

    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1.0);
    _texCoords = texCoords;
    _normal = normal;

}