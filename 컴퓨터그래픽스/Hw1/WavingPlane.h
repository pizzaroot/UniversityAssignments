#ifndef __WAVING_PLANE_H__
#define __WAVING_PLANE_H__

class WavingPlane {
private:
	int div, sz;
	float unit;

	vec4 *vertices, *colors;

	GLuint vao, vbo, prog;

	void __set_div(int _div) {
		div = _div; unit = radius * 2 / div;
		sz = div * div * 6;

		vertices = new vec4[sz];
		colors = new vec4[sz];
	}

	void __set_vertices(int i, int j) {
		int idx = (i * div + j) * 6;
		vertices[idx++] = vec4(-radius + unit * i, 0.f, -radius + unit * j, 1.f);
		vertices[idx++] = vec4(-radius + unit * (i + 1), 0.f, -radius + unit * j, 1.f);
		vertices[idx++] = vec4(-radius + unit * (i + 1), 0.f, -radius + unit * (j + 1), 1.f);
		vertices[idx++] = vec4(-radius + unit * (i + 1), 0.f, -radius + unit * (j + 1), 1.f);
		vertices[idx++] = vec4(-radius + unit * i, 0.f, -radius + unit * (j + 1), 1.f);
		vertices[idx++] = vec4(-radius + unit * i, 0.f, -radius + unit * j, 1.f);
	}

	void __set_colors(int i, int j) {
		int idx = (i * div + j) * 6;
		vec4 color = ((i + j) & 1) == 1 ? vec4(0.6f, 0.6f, 0.6f, 1.f) : vec4(0.5f, 0.5f, 0.5f, 1.f);
		for (int offset = 0; offset < 6; offset++) {
			colors[idx + offset] = color;
		}
	}

	void __tile(int i, int j) {
		__set_vertices(i, j);
		__set_colors(i, j);
	}

	void __init() {
		for (int i = 0; i < div; i++) {
			for (int j = 0; j < div; j++) __tile(i, j);
		}

		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);

		glBufferData(GL_ARRAY_BUFFER, sizeof(vec4) * sz * 2, NULL, GL_STATIC_DRAW);

		glBufferSubData(GL_ARRAY_BUFFER, 0, sizeof(vec4) * sz, vertices);
		glBufferSubData(GL_ARRAY_BUFFER, sizeof(vec4) * sz, sizeof(vec4) * sz, colors);

		GLuint vPosition = glGetAttribLocation(prog, "vPosition");
		glEnableVertexAttribArray(vPosition);
		glVertexAttribPointer(vPosition, 4, GL_FLOAT, GL_FALSE, 0, BUFFER_OFFSET(0));

		GLuint vColor = glGetAttribLocation(prog, "vColor");
		glEnableVertexAttribArray(vColor);
		glVertexAttribPointer(vColor, 4, GL_FLOAT, GL_FALSE, 0, BUFFER_OFFSET(sizeof(vec4) * sz));

		delete[] vertices;
		delete[] colors;

		vertices = nullptr;
		colors = nullptr;
	}

	void __log_info() {
#ifdef _DEBUG
		std::cout << "Division: " << div << ", # of Triangles: " << sz / 3 << ", # of Vertices: " << sz << '\n';
#endif
	}
public:
	const static int MAX_DIV = 200;
	float cur_time, wave_size, amplitude, radius;

	WavingPlane(int _div, float _r, float _amp) {
		cur_time = 0;
		wave_size = 0;
		radius = _r;
		amplitude = _amp;

		glGenVertexArrays(1, &vao);
		glGenBuffers(1, &vbo);

		__set_div(std::max(2, std::min(_div, MAX_DIV)));

		prog = InitShader("vshader.glsl", "fshader.glsl");
		__init();
		__log_info();
	}

	void division(int _div) {
		if (_div < 2) return;
		if (_div > MAX_DIV) return;

		__set_div(_div);
		__init();
		__log_info();
	}

	void draw() {
		glBindVertexArray(vao);
		glUseProgram(prog);

		GLuint time = glGetUniformLocation(prog, "uTime");
		GLuint wave = glGetUniformLocation(prog, "waveSize");
		GLuint prad = glGetUniformLocation(prog, "planeRadius");

		glUniform1f(time, cur_time);
		glUniform1f(wave, wave_size);
		glUniform1f(prad, radius);
		
		glDrawArrays(GL_TRIANGLES, 0, sz);
	}
};

#endif