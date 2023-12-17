import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // Set initial values
	int numSnowflakes = 20;
  float[] snowflakesX = new float[numSnowflakes];
  float[] snowflakesY = new float[numSnowflakes];
  float[] snowflakesSpeed = new float[numSnowflakes];
  boolean[] ballHideStatus = new boolean[numSnowflakes];

  int numLives = 3;

  float playerX, playerY;
  float playerSpeed = 5;

  boolean upPressed = false;
  boolean downPressed = false;
  boolean leftPressed = false;
  boolean rightPressed = false;

  boolean collisionOccurred = false;

  /**
   * Called once at the beginning of execution, put your size acll in this method
   */
  public void settings() {
	// put your size call here
    size(600, 400);
  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    background(0,0,0);
    // Set player position
    playerX = width / 2;
    playerY = height - 30;

    // Set snowflakes
    for (int i = 0; i < numSnowflakes; i++) {
      snowflakesX[i] = random(width);
      snowflakesY[i] = random(height);
      snowflakesSpeed[i] = random(1, 2);
      ballHideStatus[i] = false; // all snowflakes will start as visible
    }
  }

  public void draw() {
    // Update background
    background(0,0,0);

    // Draw lives
    drawLives();

    // Draw player
    fill(0, 0, 255);
    ellipse(playerX, playerY, 20, 20);

    // Move player based on user input  
    if (upPressed){
      playerY -= 3;
    }
    if (downPressed){
      playerY += 3;
    }
    if (leftPressed){
      playerX -= 3;
    }
    if (rightPressed){
      playerX += 3;
    }
    
    // Drawing Snowflakes
    for (int i = 0; i < numSnowflakes; i++) {
      // Check if snowflake is visible and draw
      if (!ballHideStatus[i]) {
        fill(255, 255, 255);
        ellipse(snowflakesX[i], snowflakesY[i], 15, 15);

        // Check for collision with player
      if (dist(playerX, playerY, snowflakesX[i], snowflakesY[i]) < 15 && !collisionOccurred) {
        // Player loses a life
        if (numLives > 0) {
          numLives--;
          resetPlayer();
          collisionOccurred = true;
      }
    }
  }
      
      // Update snowflake position
      snowflakesY[i] += snowflakesSpeed[i];
      


      // Wrap around screen
      if (snowflakesY[i] > height) {
        snowflakesY[i] = 0;
        snowflakesX[i] = random(width);
      }
    }

      // Reset collisionOccured back to false when the player collides.  
      if (collisionOccurred) {
        collisionOccurred = false;
      }

      // Check for mouse click on snowflakes
      mouseClicked();

    // Check for game over
    if (numLives <= 0) {
      background(255);
      fill(0);
      textSize(30);
      textAlign(CENTER, CENTER);
      text("Game Over", width / 2, height / 2);
    }
  }
  
  // Method for the keyboard input.  Takes input from UP/DOWN arrow keys and WASD.
  public void keyPressed() {
    // Pressing UP will slow down the snow by 50%, and pressing DOWN will speed it up by 200%.
    if (keyCode == UP) {
      // Slow down snowfall
      for (int i = 0; i < numSnowflakes; i++) {
        snowflakesSpeed[i] *= 0.5;
      }
    } else if (keyCode == DOWN) {
      // Speed up snowfall
      for (int i = 0; i < numSnowflakes; i++) {
        snowflakesSpeed[i] *= 2.0;
      }
    }

    // When the user presses WASD, it sets the booleans to true to allow the player to move.
    if (key == 'w') {
      upPressed = true;
    }

    if (key == 's') {
      downPressed = true;
    }

    if (key == 'a') {
      leftPressed = true;
    }

    if (key == 'd') {
      rightPressed = true;
    }
  }

  // Draws out the cubes that indicate the player's lives
  public void drawLives() {
    for (int i = 0; i < numLives; i++) {
      fill(245, 66, 66);
      rect(width - 30 - i * 30, 10, 20, 20);
    }
  }

  // Will make the player stop moving as soon as the release the WASD keys.
  public void keyReleased() {
    if (key == 'w') {
      upPressed = false;
    }

    if (key == 's') {
      downPressed = false;
    }

    if (key == 'a') {
      leftPressed = false;
    }

    if (key == 'd') {
      rightPressed = false;
    }
  }

  // When the player collides with a snowflake, they will be reset back to the starting position
  public void resetPlayer() {
    playerX = width / 2;
    playerY = height - 30;
  }

  // Will make the snowflake dissapear when it is clicked by the mouse
  public void mouseClicked() {
    for (int i = 0; i < numSnowflakes; i++) {
      // Check if the mouse is over the visible snowflake
      if (!ballHideStatus[i] && dist(mouseX, mouseY, snowflakesX[i], snowflakesY[i]) < 7.5) {
        if (mousePressed) {
          // Set the visibility of the snowflake to false (hidden)
          ballHideStatus[i] = true;
        }  
      }
    }
  }
}