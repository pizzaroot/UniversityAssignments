#version 330
#define M_PI 3.1415926535897932384626433832795

uniform float uTime;
uniform float waveSize;
uniform float planeRadius;

in vec4 vPosition;
in vec4 vColor;

out vec4 color;
out vec4 oPosition;
out vec4 rPos;

void main() {
	mat3 rotX, rotY;

	float thetaX = -45 / 180.0 * M_PI;
	float thetaY = -uTime;

	rotX[0] = vec3(1, 0, 0);
	rotX[1] = vec3(0, cos(thetaX), sin(thetaX));
	rotX[2] = vec3(0, -sin(thetaX), cos(thetaX));
	
	rotY[0] = vec3(cos(thetaY), 0, -sin(thetaY));
	rotY[1] = vec3(0, 1, 0);
	rotY[2] = vec3(sin(thetaY), 0, cos(thetaY));

	float dist = sqrt(vPosition.x * vPosition.x + vPosition.z * vPosition.z);
	float amp = exp(-3.55 * dist * dist / planeRadius / planeRadius) * waveSize;
	vec3 newbPos = vec3(vPosition.x, amp * sin(4 * M_PI * (-uTime + 1.6 * dist / planeRadius)), vPosition.z);

	vec3 newPos = rotX * rotY * newbPos.xyz;

	gl_Position = vec4(newPos, 1);

	color = vColor;
	oPosition = vec4(newbPos, 1);
}