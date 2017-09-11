#version 330



// Entrada
in vec4 Normal;
in vec4 outPos;
in vec4 FragPos;
in vec4 outColor;

uniform mat4 modelmatrix;
uniform mat4 viewmatrix;
uniform mat4 projection;

//light parameters
uniform vec3 lightPos;
uniform vec3 ambientColor;
uniform vec3 diffuseColor;
uniform vec3 speclarColor;
uniform float kA, kD, kS, sN;

//Saida
out vec4 theColor;

void main()
{
  mat4 modelView = viewmatrix * modelmatrix;
  //diffuse
  vec3 lightDir = normalize(lightPos - FragPos.xyz);
  float iD = max(0.0, dot(lightDir, Normal.xyz));

  //specular
  vec3  v  = -normalize((modelView * outPos).xyz);
  vec3  h  =  normalize(lightDir + v);
  float iS =  pow(max(0.0, dot(Normal.xyz, h)), sN);

  vec3 lightFactor = kA * ambientColor + kD * iD * diffuseColor + kS * iS * speclarColor;

  theColor = vec4(outColor.rgb * lightFactor, outColor.a);
}
