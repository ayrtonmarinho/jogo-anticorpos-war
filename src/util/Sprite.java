/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author iTuhh Z
 */
public class Sprite {

    public Vector postion;
    public Vector velocity;
    public double rotation;
    public Rectangle boundary;
    public Image image;
    public double elapsedTime; // Segundos

    public Sprite() {
        this.postion = new Vector();
        this.velocity = new Vector();
        this.rotation = 0;
        this.boundary = new Rectangle();
        this.elapsedTime = 0;
    }

    public Sprite(String imageFileName) {
        this();
        setImage(imageFileName);
    }

    public void setImage(String imageFileName) {
        this.image = new Image(imageFileName);
        this.boundary.setSize(this.image.getWidth(), this.image.getHeight());
    }

    public Rectangle getBoundary() {
        this.boundary.setPosition(this.postion.x, this.postion.y);
        return this.boundary;
    }

    public boolean overlaps(Sprite other) {
        return this.getBoundary().overlaps(other.getBoundary());
    }

    public void wrap(double screenWidth, double screenHeight) {
        double halfWidth = this.image.getWidth() / 2;
        double halfHeight = this.image.getHeight() / 2;

        if (this.postion.x + halfWidth < 0) {
            this.postion.x = screenWidth + halfWidth;
        }
        if (this.postion.x > screenWidth + halfWidth) {
            this.postion.x = -halfWidth;
        }
        if (this.postion.y + halfHeight < 0) {
            this.postion.y = screenHeight+halfHeight;
        }
        if (this.postion.y > screenHeight+halfHeight) {
            this.postion.y = -halfHeight;
        }
    }

    public void update(double deltaTime) {
        //Increase elapsedTime
        this.elapsedTime += deltaTime;
        
        //upate a posição de acordo com a velocity.
        this.postion.add(this.velocity.x * deltaTime, this.velocity.y * deltaTime);
        //wrap around screen
        this.wrap(800, 600);
    }

    public void render(GraphicsContext context) {
        context.save();

        context.translate(this.postion.x, this.postion.y);
        context.rotate(this.rotation);
        context.translate(-this.image.getWidth() / 2, -this.image.getHeight() / 2);
        context.drawImage(this.image, 0, 0);

        context.restore();
    }
}
