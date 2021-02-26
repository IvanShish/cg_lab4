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

//        Color color = new Color(1, 0, 0);


        for (int i = 0; i < 10; i++) {
            gl.glColor3f((float)Math.random(), (float)Math.random(), (float)Math.random());
            gl.glVertex3f(center.x, center.y, center.z);
            gl.glVertex3f(topPoint.x, topPoint.y, topPoint.z);
            gl.glVertex3f(bottomPoint.x, bottomPoint.y, bottomPoint.z);

            if (i%2 == 0) {
                if (deep < 5) {
                    drawStar(gl, topPoint, size / 3, deep + 1);
                }
                topPoint = turnPoint(topPoint, center, (float)Math.PI * 2/5);
            } else {
                bottomPoint = turnPoint(bottomPoint, center, (float)Math.PI * 2/5);
            }
//            color = turnColor(color, 1);
        }

    }

    private Point turnPoint(Point point, Point center, float angle) {
        Point res = new Point();
        res.x = center.x + (point.x - center.x) * (float)Math.cos(angle) - (point.y - center.y) * (float)Math.sin(angle);
        res.y = center.y + (point.x - center.x) * (float)Math.sin(angle) + (point.y - center.y) * (float)Math.cos(angle);
        res.z = point.z;
        return res;
    }

    private Color turnColor(Color color, float angle) {
        return new Color();
    }
}
