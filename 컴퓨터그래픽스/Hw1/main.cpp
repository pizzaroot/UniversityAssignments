#include <vgl.h>
#include <InitShader.h>
#include <vec.h>
#include "WavingPlane.h"

WavingPlane *plane;

bool bPlay = false;
int divCnt = 30;
float curAmplitude = 0.3;
float planeRadius = 0.8;

void display() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glEnable(GL_DEPTH_TEST); 

	plane->draw();

	glutSwapBuffers();
}

void idle() {
	if (!bPlay) return;

	Sleep(16);
	plane->cur_time += 0.01;

	glutPostRedisplay();
}

void keyboard(unsigned char ch, int x, int y) {
	if ((ch | 32) == 'w') {
		plane->wave_size = plane->amplitude - plane->wave_size;
	}
	else if ((ch | 32) == 'q') {
		delete plane;
		glutExit();
	}
	else if (ch == ' ') {
		bPlay = !bPlay;
	}
	else if (ch == '1') {
		if (divCnt > 2) plane->division(--divCnt);
	}
	else if (ch == '2') {
		if (divCnt < WavingPlane::MAX_DIV) plane->division(++divCnt);
	}

	glutPostRedisplay();
}

int main(int argc, char **argv) {
	std::cout << "A Waving Color Plane\n";

	std::cout << "Programming Assignment #1 for Computer Graphics\n";
	std::cout << "Department of Software, Sejong University\n\n";

	std::cout << "----------------------------------------------------------------\n";

	std::cout << "'1' key: Decreasing the number of division\n";
	std::cout << "'2' key: Increasing the number of division\n";
	std::cout << "'W' key: Showing/hiding the waving pattern\n";
	std::cout << "Spacebar: Starting/stoping rotating and waving\n\n";

	std::cout << "'Q' Key: Exit the program\n";

	std::cout << "----------------------------------------------------------------\n\n";

	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH);
	glutInitWindowSize(512, 512);
	glutCreateWindow("Waving Plane");

	glewExperimental = true;
	glewInit();

	plane = new WavingPlane(divCnt, planeRadius, curAmplitude);

	glutDisplayFunc(display);
	glutIdleFunc(idle);
	glutKeyboardFunc(keyboard);

	glutMainLoop();

	return 0;
}