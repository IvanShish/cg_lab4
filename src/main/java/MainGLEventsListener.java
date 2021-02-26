import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.nio.FloatBuffer;

public class MainGLEventsListener implements GLEventListener {


    public void init(GLAutoDrawable glAutoDrawable) {

    }

    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glBegin(GL2.GL_TRIANGLES);
        drawStar(gl, new Point(0, 0, 0), 0.5f, 0);
        gl.glEnd();
        gl.glDisable(GL2.GL_DEPTH_TEST);
    }

    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    private void drawStar(GL2 gl, Point center, float size, int deep) {
        Point topPoint = new Point(center.x, center.y + size, center.z + size/3);
        Point bottomPoint = turnPoint(topPoint, center, (float)Math.PI * 2/10);
        bottomPoint.y = (center.y + 2 * bottomPoint.y) / (1 + 2);
        bottomPoint.x = (center.x + 2 * bottomPoint.x) / (1 + 2);

        Point centerOut = new Point(center.x, center.y, center.z + size);
        Point topOut = new Point(bottomPoint.x + (bottomPoint.x - center.x) * 1.5f, bottomPoint.y + (bottomPoint.y - center.y) * 1.5f, bottomPoint.z + size);
        Point bottomOut = new Point(topPoint.x + (topPoint.x - center.x) * 0.4f, topPoint.y + (topOut.y - center.y) * 0.4f, topPoint.z + size);

        Color firstColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        Color color = new Color(firstColor.r/10, firstColor.g/10, firstColor.b/10);
        Color angle = color;

        Color secondColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        Color colorOut = new Color(secondColor.r/10, secondColor.g/10, secondColor.b/10);
        Color angleOut = colorOut;


        for (int i = 0; i < 10; i++) {
            gl.glColor3f(color.r, color.g, color.b);
            gl.glVertex3f(center.x, center.y, center.z);
            gl.glVertex3f(topPoint.x, topPoint.y, topPoint.z);
            gl.glVertex3f(bottomPoint.x, bottomPoint.y, bottomPoint.z);

            gl.glColor3f(colorOut.r, colorOut.g, colorOut.b);
            gl.glVertex3f(centerOut.x, centerOut.y, centerOut.z);
            gl.glVertex3f(topOut.x, topOut.y, topOut.z);
            gl.glVertex3f(bottomOut.x, bottomOut.y, bottomOut.z);

            if (i%2 == 0) {
                if (deep < 2) {
                    drawStar(gl, topPoint, size / 3, deep + 1);
                }
                topPoint = turnPoint(topPoint, center, (float)Math.PI * 2/5);
                bottomOut = turnPoint(bottomOut, centerOut, (float)Math.PI * 2/5);

            } else {
                bottomPoint = turnPoint(bottomPoint, center, (float)Math.PI * 2/5);

                topOut = turnPoint(topOut, centerOut, (float)Math.PI * 2/5);
            }
            color = turnColor(color, angle);
            colorOut = turnColor(colorOut, angleOut);
        }

    }

    private Point turnPoint(Point point, Point center, float angle) {
        Point res = new Point();
        res.x = center.x + (point.x - center.x) * (float)Math.cos(angle) - (point.y - center.y) * (float)Math.sin(angle);
        res.y = center.y + (point.x - center.x) * (float)Math.sin(angle) + (point.y - center.y) * (float)Math.cos(angle);
        res.z = point.z;
        return res;
    }

    private Color turnColor(Color color, Color angle) {
        Color res = new Color();
        res.r = color.r + angle.r;
        res.g = color.g + angle.g;
        res.b = color.b + angle.b;
        return res;
    }
}
