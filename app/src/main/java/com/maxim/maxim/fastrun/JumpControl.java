package com.maxim.maxim.fastrun;

public class JumpControl {
    private int startJumpSpeed = 0;
    private int acceleration = 0;
    private int currentSpeed = 0;

    public void initPhysicsParams(int startJumpSpeedParam, int accelerationParam) {
        startJumpSpeed = startJumpSpeedParam;
        acceleration = accelerationParam;
    }

    private int ground = 0;
    private int heroPos = 0;
    private int heroSize = 0;

    public int getHeroY() {
        return heroPos;
    }

    public void initStartingParams(int groundParam, int startHeroPosParam, int heroSizeParam) {
        ground = groundParam;
        heroPos = startHeroPosParam;
        heroSize = heroSizeParam;
    }

    private boolean move = false;

    public void move() {
        if(move) {
            heroPos -= currentSpeed;
            currentSpeed -= acceleration;
            if(heroPos + heroSize >= ground) {
                move = false;
                canJump = true;
                heroPos = ground - heroSize;
            }
        }
    }

    private boolean canJump = true;

    public void jump() {
        if(canJump) {
            canJump = false;
            move = true;
            currentSpeed = startJumpSpeed;
        }
    }
}
