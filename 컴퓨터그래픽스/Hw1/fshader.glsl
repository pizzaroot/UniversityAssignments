#version 330

in vec4 color;
in vec4 oPosition;
in vec4 rPos;

out vec4 fColor;

void main() {
	if (rPos.x < -1.0 || rPos.x > 1.0) discard;
	if (rPos.y < -1.0 || rPos.y > 1.0) discard;
	fColor = color + vec4(2 * oPosition.y, 0.0, -2 * oPosition.y, 0.0);
}