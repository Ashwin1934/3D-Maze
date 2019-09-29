import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Timer;




public class Maze extends JPanel implements KeyListener,MouseListener
{
	JFrame frame;
	//maze array
	char[][] mazeLayout = new char[21][31];
	int xPos=16,yPos=30;
	int orientation = 1;
	int counter;
	boolean gameOver = false;
	boolean currentScreen = true;
	public Maze()
	{
		setBoard();
		frame=new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,800);
		frame.setVisible(true);
		frame.addKeyListener(this);



		//Audio clip add on
		try{
		final Clip audio = AudioSystem.getClip();
		final AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("GobletOfFire.wav"));
		audio.open(inputStream);
		audio.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){}

//timer add on attempt
/*
 new CountDownTimer(30000,1000) {
            public void onTick(long l) {
                time=(int)l/1000;
            }

            @Override
            public void onFinish() {
                time=0;
                stop = true;
            }
        }.start();*/


	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);	//background color
		g.fillRect(0,0,1000,800);
		g.setColor(Color.WHITE);
		//drawBoard
		for(int j=0;j<mazeLayout[0].length;j++)
			for(int i=0;i<mazeLayout.length;i++){
				if(mazeLayout[i][j]!='-'){
					g.setColor(Color.GRAY);
					g.fillRect(j*10,i*10,10,10);
					g.setColor(Color.WHITE);
					g.drawRect(j*10,i*10,10,10);
					if(mazeLayout[i][j]=='@'){
						g.setColor(Color.RED);
						g.fillRect(j*10,i*10,10,10);
					}
				}
			}

		//object
		g.setColor(Color.RED);
		g.fillRect(yPos*10, xPos*10, 10, 10);
		g.setFont(new Font("Ariel",Font.PLAIN,15));
		g.drawString("Count: "+counter,800,50);
		//determinations
		if(xPos == 0 && yPos == 10){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1000,800);
			g.setColor(Color.RED);
			g.setFont(new Font("Ariel",Font.PLAIN,30));
			g.drawString("You did it! Voldemort's back tho!",250,90);
			//part of add on component
			g.drawString("Click R to Restart.",250,120);
		}
		///Add on(return the character back to beginning)
		if(xPos == 13 && yPos == 1){
			g.setFont(new Font("Ariel",Font.PLAIN,15));
			g.drawString("You apparated back!Find Cedric quick!",730,70);
			xPos = 16;
			yPos = 30;
		}
		if(xPos == 2 && yPos == 10){
			g.setFont(new Font("Ariel",Font.PLAIN,15));
			g.drawString("DEATH EATERS!HELL NO!",770,70);
			xPos = 16;
			yPos = 30;
			orientation =1;
		}
		if(xPos == 6 && yPos == 13){
			g.setFont(new Font("Ariel",Font.PLAIN,15));
			g.drawString("PEOPLE WILL DIE!!HURRY UP!",760,70);
		}


		try{

		if(orientation == 1){
			for(int i=1;i<=3;i++){
				//right wall
				if(mazeLayout[xPos-1][yPos-i] != '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(rightWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(rightWall(i));
				}else if(mazeLayout[xPos-1][yPos-i] == '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(topRight(i));
					g.drawPolygon(bottomRight(i));
					g.drawPolygon(midRight(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topRight(i));
					g.fillPolygon(bottomRight(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(midRight(i));
				}else if(mazeLayout[xPos-1][yPos-1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(topRight(1));
					g.drawPolygon(bottomRight(1));
					g.drawPolygon(midRight(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topRight(1));
					g.fillPolygon(bottomRight(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(midRight(1));
				}else if(mazeLayout[xPos-1][yPos-1] != '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(rightWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(rightWall(1));
				}
				//left wall
				if(mazeLayout[xPos+1][yPos-i] != '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(leftWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(leftWall(i));
				}else if(mazeLayout[xPos+1][yPos-i] == '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(topLeft(i));
					g.drawPolygon(bottomLeft(i));
					g.drawPolygon(midLeft(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topLeft(i));
					g.fillPolygon(bottomLeft(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(midLeft(i));
				}else if(mazeLayout[xPos+1][yPos-1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(topLeft(1));
					g.drawPolygon(bottomLeft(1));
					g.drawPolygon(midLeft(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topLeft(1));
					g.fillPolygon(bottomLeft(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(midLeft(1));
				}else if(mazeLayout[xPos+1][yPos-1] != '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(leftWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(leftWall(1));
				}
				if(mazeLayout[xPos][yPos-i] == '-' && !gameOver){
					//floor
					g.setColor(Color.WHITE);
					g.drawPolygon(floor(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(floor(i));
					//ceiling
					g.setColor(Color.WHITE);
					g.drawPolygon(ceiling(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(ceiling(i));
				}else if(mazeLayout[xPos][yPos-1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(floor(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(floor(1));
					g.setColor(Color.WHITE);
					g.drawPolygon(ceiling(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(ceiling(1));
				}
				//end wall
				if(mazeLayout[xPos][yPos-1] !='-'){
					gameOver = true;
					g.setColor(Color.WHITE);
					g.drawPolygon(endWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(endWall(1));
				}else if(mazeLayout[xPos][yPos-i] != '-' && !gameOver){
					gameOver = true;
					g.setColor(Color.WHITE);
					g.drawPolygon(endWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(endWall(i));
				}else
					gameOver = false;
			}
		}
		if(orientation == 3){
			for(int i=1;i<=3;i++){
				//right wall
				if(mazeLayout[xPos+1][yPos+i] != '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(rightWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(rightWall(i));
				}else if(mazeLayout[xPos+1][yPos+i] == '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(topRight(i));
					g.drawPolygon(bottomRight(i));
					g.drawPolygon(midRight(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topRight(i));
					g.fillPolygon(bottomRight(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(midRight(i));
				}else if(mazeLayout[xPos+1][yPos+1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(topRight(1));
					g.drawPolygon(bottomRight(1));
					g.drawPolygon(midRight(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topRight(1));
					g.fillPolygon(bottomRight(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(midRight(1));
				}else if(mazeLayout[xPos+1][yPos+1] != '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(rightWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(rightWall(1));
				}
				//left wall
				if(mazeLayout[xPos-1][yPos+i] != '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(leftWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(leftWall(i));
				}else if(mazeLayout[xPos-1][yPos+i] == '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(topLeft(i));
					g.drawPolygon(bottomLeft(i));
					g.drawPolygon(midLeft(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topLeft(i));
					g.fillPolygon(bottomLeft(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(midLeft(i));
				}else if(mazeLayout[xPos-1][yPos+1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(topLeft(1));
					g.drawPolygon(bottomLeft(1));
					g.drawPolygon(midLeft(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topLeft(1));
					g.fillPolygon(bottomLeft(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(midLeft(1));
				}else if(mazeLayout[xPos-1][yPos+1] != '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(leftWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(leftWall(1));
				}
				if(mazeLayout[xPos][yPos+i] == '-' && !gameOver){
					//floor
					g.setColor(Color.WHITE);
					g.drawPolygon(floor(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(floor(i));
					//ceiling
					g.setColor(Color.WHITE);
					g.drawPolygon(ceiling(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(ceiling(i));
				}else if(mazeLayout[xPos][yPos+1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(floor(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(floor(1));
					g.setColor(Color.WHITE);
					g.drawPolygon(ceiling(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(ceiling(1));
				}
				//end wall
				if(mazeLayout[xPos][yPos+1] !='-'){
					gameOver = true;
					g.setColor(Color.WHITE);
					g.drawPolygon(endWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(endWall(1));
				}else if(mazeLayout[xPos][yPos+i] != '-' && !gameOver){
					gameOver = true;
					g.setColor(Color.WHITE);
					g.drawPolygon(endWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(endWall(i));
				}else
					gameOver = false;
			}
		}
		if(orientation == 4){
			for(int i=1;i<=3;i++){
				//right wall
				if(mazeLayout[xPos-i][yPos+1] != '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(rightWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(rightWall(i));
				}else if(mazeLayout[xPos-i][yPos+1] == '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(topRight(i));
					g.drawPolygon(bottomRight(i));
					g.drawPolygon(midRight(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topRight(i));
					g.fillPolygon(bottomRight(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(midRight(i));
				}else if(mazeLayout[xPos-1][yPos+1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(topRight(1));
					g.drawPolygon(bottomRight(1));
					g.drawPolygon(midRight(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topRight(1));
					g.fillPolygon(bottomRight(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(midRight(1));
				}else if(mazeLayout[xPos-1][yPos+1] != '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(rightWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(rightWall(1));
				}
				//left wall
				if(mazeLayout[xPos-i][yPos-1] != '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(leftWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(leftWall(i));
				}else if(mazeLayout[xPos-i][yPos-1] == '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(topLeft(i));
					g.drawPolygon(bottomLeft(i));
					g.drawPolygon(midLeft(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topLeft(i));
					g.fillPolygon(bottomLeft(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(midLeft(i));
				}else if(mazeLayout[xPos-1][yPos-1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(topLeft(1));
					g.drawPolygon(bottomLeft(1));
					g.drawPolygon(midLeft(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topLeft(1));
					g.fillPolygon(bottomLeft(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(midLeft(1));
				}else if(mazeLayout[xPos-1][yPos-1] != '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(leftWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(leftWall(1));
				}
				if(mazeLayout[xPos-i][yPos] == '-' && !gameOver){
					//floor
					g.setColor(Color.WHITE);
					g.drawPolygon(floor(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(floor(i));
					//ceiling
					g.setColor(Color.WHITE);
					g.drawPolygon(ceiling(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(ceiling(i));
				}else if(mazeLayout[xPos-1][yPos] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(floor(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(floor(1));
					g.setColor(Color.WHITE);
					g.drawPolygon(ceiling(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(ceiling(1));
				}
				//end wall
				if(mazeLayout[xPos-1][yPos] != '-'){
					gameOver = true;
					g.setColor(Color.WHITE);
					g.drawPolygon(endWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(endWall(1));
				}else if(mazeLayout[xPos-i][yPos] != '-' && !gameOver){
					gameOver = true;
					g.setColor(Color.WHITE);
					g.drawPolygon(endWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(endWall(i));
				}else
					gameOver = false;
			}
		}
		if(orientation == 2){
			for(int i=1;i<=3;i++){
				//right wall
				if(mazeLayout[xPos+i][yPos-1] != '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(rightWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(rightWall(i));
				}else if(mazeLayout[xPos+i][yPos-1] == '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(topRight(i));
					g.drawPolygon(bottomRight(i));
					g.drawPolygon(midRight(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topRight(i));
					g.fillPolygon(bottomRight(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(midRight(i));
				}else if(mazeLayout[xPos+1][yPos-1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(topRight(1));
					g.drawPolygon(bottomRight(1));
					g.drawPolygon(midRight(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topRight(1));
					g.fillPolygon(bottomRight(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(midRight(1));
				}else if(mazeLayout[xPos+1][yPos-1] != '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(rightWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(rightWall(1));
				}
				//left wall
				if(mazeLayout[xPos+i][yPos+1] != '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(leftWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(leftWall(i));
				}else if(mazeLayout[xPos+i][yPos+1] == '-' && !gameOver){
					g.setColor(Color.WHITE);
					g.drawPolygon(topLeft(i));
					g.drawPolygon(bottomLeft(i));
					g.drawPolygon(midLeft(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topLeft(i));
					g.fillPolygon(bottomLeft(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(midLeft(i));
				}else if(mazeLayout[xPos+1][yPos+1] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(topLeft(1));
					g.drawPolygon(bottomLeft(1));
					g.drawPolygon(midLeft(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(topLeft(1));
					g.fillPolygon(bottomLeft(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(midLeft(1));
				}else if(mazeLayout[xPos+1][yPos+1] != '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(leftWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(leftWall(1));
				}
				if(mazeLayout[xPos+i][yPos] == '-' && !gameOver){
					//floor
					g.setColor(Color.WHITE);
					g.drawPolygon(floor(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(floor(i));
					//ceiling
					g.setColor(Color.WHITE);
					g.drawPolygon(ceiling(i));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(ceiling(i));
				}else if(mazeLayout[xPos+1][yPos] == '-'){
					g.setColor(Color.WHITE);
					g.drawPolygon(floor(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(floor(1));
					g.setColor(Color.WHITE);
					g.drawPolygon(ceiling(1));
					g.setColor(Color.DARK_GRAY);
					g.fillPolygon(ceiling(1));
				}
				//emd wall
				if(mazeLayout[xPos+1][yPos] != '-'){
					gameOver = true;
					g.setColor(Color.WHITE);
					g.drawPolygon(endWall(1));
					g.setColor(Color.GRAY);
					g.fillPolygon(endWall(1));
				}else if(mazeLayout[xPos+i][yPos] != '-' && !gameOver){
					gameOver = true;
					g.setColor(Color.WHITE);
					g.drawPolygon(endWall(i));
					g.setColor(Color.GRAY);
					g.fillPolygon(endWall(i));
				}else
					gameOver = false;
			}
			}
		}catch(Exception E){}

	}
	public void setBoard()
	{


		File name = new File("Maze.txt");
		int r=0;
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			int x=0;
			while((text=input.readLine())!= null)
			{
					for(int y=0;y<text.length();y++){
						mazeLayout[x][y] = text.charAt(y);
					}
					x++;

			}
		}
		catch (IOException io)
		{
			System.err.println("File error");
		}


	}
	public void keyPressed(KeyEvent e)
	{
		try{
			///restart button add on
			if(e.getKeyCode() == KeyEvent.VK_R){
				xPos = 16;
				yPos = 30;
				counter = 0;
				frame.repaint();
			}
			if(e.getKeyCode() == KeyEvent.VK_UP){
				if(orientation == 1 && mazeLayout[xPos][yPos-1] == '-'){
					counter++;
					yPos-=1;
				}else if(orientation == 2 && mazeLayout[xPos+1][yPos] == '-'){
					counter++;
					xPos+=1;
				}else if(orientation == 3 && mazeLayout[xPos][yPos+1] == '-'){
					counter++;
					yPos+=1;
				}else if(orientation == 4 && mazeLayout[xPos-1][yPos] == '-'){
					counter++;
					xPos-=1;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				if(orientation == 1 && mazeLayout[xPos][yPos+1] == '-'){
					counter++;
					yPos+=1;
				}else if(orientation == 2 && mazeLayout[xPos-1][yPos] == '-'){
					counter++;
					xPos-=1;
				}else if(orientation == 3 && mazeLayout[xPos][yPos-1] == '-'){
					counter++;
					yPos-=1;
				}else if(orientation == 4 && mazeLayout[xPos+1][yPos] == '-'){
					counter++;
					xPos+=1;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				orientation-=1;
				if(orientation > 4)
					orientation = 1;
				else if(orientation < 1)
					orientation = 4;

			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				orientation+=1;
				if(orientation > 4)
					orientation = 1;
				else if(orientation < 1)
					orientation = 4;
			}
			frame.repaint();
		}catch(Exception E){

		}

	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public Polygon topRight(int a){
		int scale = 50*a;
		int[] xP = {950-scale,1000-scale,1000-scale};
		int[] yP = {150+scale,150+scale,100+scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon bottomRight(int a){
		int scale = 50*a;
		int[] xP = {950-scale,1000-scale,1000-scale};
		int[] yP = {550-scale,550-scale,600-scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon midRight(int a){
		int scale = 50*a;
		int[] xP = {950-scale,1000-scale,1000-scale,950-scale};
		int[] yP = {150+scale,150+scale,550-scale,550-scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon topLeft(int a){
		int scale = 50*a;
		int[] xP = {500+scale,550+scale,500+scale};
		int[] yP = {150+scale,150+scale,100+scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon bottomLeft(int a){
		int scale = 50*a;
		int[] xP = {500+scale,550+scale,500+scale};
		int[] yP = {550-scale,550-scale,600-scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon midLeft(int a){
		int scale = 50*a;
		int[] xP = {500+scale,550+scale,550+scale,500+scale};
		int[] yP = {150+scale,150+scale,550-scale,550-scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon rightWall(int a){
		int scale = 50*a;
		int[] xP = {1000-scale,1000-scale,950-scale,950-scale};
		int[] yP = {100+scale,600-scale,550-scale,150+scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon leftWall(int a){
		int scale = 50*a;
		int[] xP = {500+scale,500+scale,550+scale,550+scale};
		int[] yP = {600-scale,100+scale,150+scale,550-scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon floor(int a){
		int scale = 50*a;
		int[] xP = {500+scale,550+scale,950-scale,1000-scale};
		int[] yP = {600-scale,550-scale,550-scale,600-scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon ceiling(int a){
		int scale = 50*a;
		int[] xP = {500+scale,550+scale,950-scale,1000-scale};
		int[] yP = {100+scale,150+scale,150+scale,100+scale};
		return new Polygon(xP,yP,xP.length);
	}
	public Polygon endWall(int a){
		int scale = 50*(3-a);
		int[] xP = {650-scale,850+scale,850+scale,650-scale};
		int[] yP = {250-scale,250-scale,450+scale,450+scale};
		return new Polygon(xP,yP,xP.length);
	}

	public static void main(String args[])
	{
		Maze app=new Maze();
	}
}