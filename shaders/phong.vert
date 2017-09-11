#version 330

// Entrada
layout (location = 0) in vec4 position;
layout (location = 1) in vec4 normal;
layout (location = 2) in vec4 color;

// saida
out vec4 Normal;
out vec4 FragPos;
out vec4 outColor;
out vec4 outPos;

//matrix
uniform mat4 modelmatrix;
uniform mat4 viewmatrix;
uniform mat4 projection;



void main()
{
    mat4 modelView = viewmatrix * modelmatrix;
    mat4 normalMatrix = transpose(inverse(modelView));

    // final vertex position
    gl_Position = projection * modelView * position;

    FragPos = modelmatrix * position;
    outPos = position;
    Normal = normalize(normalMatrix * normal);

    outColor = color;
}
