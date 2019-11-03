#version 400 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormal;
layout (location = 2) in vec2 aTexCoord;

out vec2 TexCoord;
out vec3 Normal;
out vec3 toLightVector;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    vec4 worldPosition = model * vec4(aPos.x, aPos.y, aPos.z, 1.0);
    gl_Position = projection * view * worldPosition;
    TexCoord = aTexCoord;

    Normal = (model * vec4(aNormal, 0.0)).xyz;

    toLightVector = vec3(200, 300, -50) - worldPosition.xyz;
}