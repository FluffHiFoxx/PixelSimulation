package org.pxlsim.materials;

import javafx.scene.paint.Color;
import org.pxlsim.Display;

import java.util.Random;

public class SandMaterial extends DynamicMaterial {
    /**
     * @param display This parameter ensures that the correct values are extracted from the current working display.
     * @param x       This material's X coordinate.
     * @param y       This material's Y coordinate.
     */
    public SandMaterial(Display display, int x, int y) {
        super(display, x, y, Color.BEIGE, display.getRefreshRate());
    }

    @Override
    public void move(Material[][] board) {
        int nextY = this.getY() + Math.min(this.getYLimit() - this.getY(), this.FALL_SPEED);
        int xModifier = new Random().nextBoolean() ? 1 : -1;
        board[this.getY()][this.getX()] = null;
        for (int i = this.getY(); i <= nextY; i++) {
            if (board[i][this.getX()] != null) {
                if (board[i][this.getX() + xModifier] == null
                        && board[this.getY()][this.getX() + xModifier] == null
                        && isInBoundsX(this.getX() + xModifier)) {
                    if (board[i][this.getX() + xModifier] instanceof WaterMaterial water) {
                        water.setY(i + 1);
                        board[water.getY()][water.getX()] = water;
                    }
                    setY(i);
                    setX(this.getX() + xModifier);
                } else if (board[i][this.getX() - xModifier] == null
                        && board[this.getY()][this.getX() - xModifier] == null
                        && isInBoundsX(this.getX() - xModifier)) {
                    if (board[i][this.getX() - xModifier] instanceof WaterMaterial water) {
                        water.setY(i + 1);
                        board[water.getY()][water.getX()] = water;
                    }
                    setY(i);
                    setX(this.getX() - xModifier);
                } else {
                    if (board[i][this.getX()] instanceof WaterMaterial water) {
                        water.setY(i - 1);
                        setY(i);
                        board[water.getY()][water.getX()] = water;
                    } else {
                        setY(i - 1);
                    }
                }
                board[this.getY()][this.getX()] = this;
                return;
            }
        }
        setY(nextY);
        board[this.getY()][this.getX()] = this;
    }
}
