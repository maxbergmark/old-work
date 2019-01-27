import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.System;

public class PlayTetris {
	
	private Tetris tetris;
	private int xsize;
	private int ysize;
	private int[][][] pieces = {{{-1, 0}, {0, 0}, {1, 0}, {2, 0}}, //long
								{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}}, //L-piece
								{{-1, 1}, {0, 1}, {1, 1}, {1, 0}}, //reverse L-piece
								{{0, 0}, {1, 0}, {0, 1}, {1, 1}}, //square
								{{-1, 1}, {0, 1}, {0, 0}, {1, 0}}, //squiggly
								{{0, 0}, {-1, 1}, {0, 1}, {1, 1}}, //T-piece
								{{-1, 0}, {0, 0}, {0, 1}, {1, 1}}}; //reverse squiggly

	private double[][] centers = {{0.5, 0.5}, {0, 1}, {0, 1}, {0.5, 0.5}, {0, 1}, {0, 1}, {0, 1}};
	private byte[] checkRotation = {2, 4, 4, 1, 2, 4, 2};
	private boolean firstGame = true;
	

	public PlayTetris(Tetris tetrisIn) {
		tetris = tetrisIn;
		xsize = tetris.getXSize();
		ysize = tetris.getYSize();
	}

	public void makeChoice() {
		if (firstGame) {
			tetris.holdMainPiece();
			firstGame = false;
			return;
		}
		byte current = tetris.getCurrentPiece();
		byte next = tetris.getNextPiece();
		byte hold = tetris.getHoldPiece();
		byte[][] tempGrid = addToGrid(tetris.getGrid(), moveShape(pieces[current], new int[] {4, 1}), (byte) 0);
		int[] holdResult = {100000, 0, 0};
		long startTime = System.nanoTime();
		int[] nextResult = playGame(tempGrid, current, next);
		long endTime = System.nanoTime();
		//System.out.println("next time: " + (endTime-startTime));
		startTime = System.nanoTime();
		if (hold >= 0 && tetris.getHoldable() && hold != next) {
			holdResult = playGame(tempGrid, hold, next); //slow, but safe
			//holdResult = playGame(tempGrid, hold, (byte)-1); //fast, but risky
		}
		endTime = System.nanoTime();
		//System.out.println("hold time: " + (endTime-startTime));

		int rot;
		int minX;
		int minMove;

		if (holdResult[0] < nextResult[0]) {
			rot = holdResult[1];
			minX = holdResult[2];
			minMove = holdResult[3];
			tetris.holdMainPiece();
		} else {
			rot = nextResult[1];
			minX = nextResult[2];
			minMove = nextResult[3];
		}

		for (int i = 0; i < rot; i++) {
			tetris.rotateMain();
		}
		tetris.movePiece(new int[] {(int) minX, 0});
		//tetris.movePiece(new int[] {0, minMove-2});
	}

	public int[] playGame(byte[][] grid, byte currentIn, byte next) {
		
		byte current = currentIn;	
		int minHoles = 1000000;
		int minRotation = 0;
		int minX = 0;
		int minMove = 0;
		

		for (int i = 0; i < checkRotation[current]; i++) {
			int[][] mainGet = intDeepCopy(pieces[current]);
			double[] centerGet = centers[current].clone();
			byte[][] tempGrid = deepCopy(grid);

			for (int r = 0; r < i; r++) {
				mainGet = rotateRight(mainGet, centerGet);
			}
			
			int[] minMax = calcMinMax(mainGet);
			int minY = calcMinMaxY(mainGet)[0];
			int[] tempDir = {(int) (-minMax[0]), (int) (-minY)};
			mainGet = moveShape(mainGet, tempDir);

			for (int j = -minMax[0]-4; j < xsize-4-minMax[1]; j++) {
				int downDist = moveDown(tempGrid, mainGet);
				int[][] shape = moveShape(mainGet, new int[] {0, downDist});
				byte[][] finalGrid;
				if (calcMinMaxY(shape)[0] > 0) {
					int[] bounds = calcMinMaxY(shape);
					finalGrid = checkRows(addToGrid(tempGrid, shape, (byte) (current+1)), bounds);
					//finalGrid = addToGrid(tempGrid, shape, (byte) (current+1));
					//onClearFilledRows(finalGrid);
					int[] nextResult;
					if (next >= 0) {
						nextResult = playGame(finalGrid, next, (byte) -1);
						if (nextResult[0] < minHoles) { 
							minHoles = nextResult[0];
							minRotation = i;
							minX = j;
							minMove = downDist;
						}
					}
					else {
						int tempHoles = calcHoles(finalGrid);
						//System.out.println(tempHoles + "   " + minHoles + "   j: " + j);
						if (tempHoles < minHoles) {
							
							minRotation = i;
							minX = j;
							minHoles = tempHoles;
							minMove = downDist;
							//System.out.println(minRotation + "   " + minX + "   " + minHoles);
						}
					}
				}
				mainGet = moveShape(mainGet, new int[] {1, 0});

			}
		}
		return new int[] {minHoles, minRotation, minX, minMove};
		
	}

	public byte[][] deepCopy(byte[][] grid) {
		if (grid == null) {
			return null;
		}
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			System.arraycopy(grid[i], 0, retGrid[i], 0, grid[i].length);
			//retGrid[i] = grid[i].clone();
		}
		return retGrid;
	}

	public int[][] intDeepCopy(int[][] grid) {
		if (grid == null) {
			return null;
		}
		int[][] retGrid = new int[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			System.arraycopy(grid[i], 0, retGrid[i], 0, grid[i].length);
			//retGrid[i] = grid[i].clone();
		}
		return retGrid;
	}

	public int[][] rotateRight(int[][] shape, double[] center) {

		double[][] transShape = new double[4][2];
		int[][] rotShape = new int[4][2];
		for (int i = 0; i < 4; i++) {
			transShape[i][0] = shape[i][0] - center[0];
			transShape[i][1] = shape[i][1] - center[1];
			rotShape[i][0] = (int) Math.round((double) (center[0] - transShape[i][1]));
			rotShape[i][1] = (int) Math.round((double) (center[1] + transShape[i][0]));
		}

		return rotShape;
	}


	public byte[][] checkRows(byte[][] grid, int[] bounds) {
		LinkedList<Integer> removalIndices = new LinkedList<Integer>();
		boolean willRemove = false;
		for (int y = bounds[0]; y < bounds[1]+1; y++) {
			boolean noZeroes = true;
			for (int x = 0; x < xsize; x++) {
				if (grid[y][x] == 0) {
					noZeroes = false;
					break;
				}
			}
			if (noZeroes) {
				willRemove = true;
				removalIndices.add(y);
			}
		}
		
		if (willRemove) {
			int[] rowArray = convert(removalIndices);
			return removeRows(grid, rowArray);
		}
		return grid;
		
	}

	public byte[][] removeRows(byte[][] grid, int[] rows) {

		int total = rows.length;
		int current = 0;
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = total; i < grid.length; i++) {
			while (current < total && i-total+current == rows[current]) {
				current++;
			}
			//retGrid[i] = grid[i-total+current].clone();
			System.arraycopy(grid[i-total+current], 0, retGrid[i], 0, xsize);
		}
		return retGrid;
	}


	public int[] convert(LinkedList<Integer> intList) {
		int[] retArray = new int[intList.size()];
		int i = 0;
		for (Integer x : intList) {
			retArray[i++] = x.intValue();
		}
		return retArray;
	}

	private static final void onClearFilledRows(final byte[][] pTetris) {
        int i = 0, j = 0;
        /* Track the last valid position of row data. */
        int lLastValidIndex = pTetris.length-1;
        /* Iterate through each row. */
        for(i = pTetris.length-1; i >= 0; i--) {
            /* Iterate through each cell in the row. */
            boolean lContainsZero = false;

            for(j = 0; j < pTetris[i].length & !lContainsZero; j++) {
                lContainsZero |= pTetris[i][j] == 0;
            }
            /* This row is valid. Copy it to the last valid index. */
            if(lContainsZero && i != lLastValidIndex) {
                System.arraycopy(pTetris[i], 0, pTetris[lLastValidIndex--], 0, pTetris[i].length);
            } else if (i == lLastValidIndex) {
            	lLastValidIndex--;
            }
        }
        /* Empty the remaining rows. */
        for(i = lLastValidIndex; i >= 0; i--) {
            for(j = 0; j < pTetris[i].length; j++) {
                /* Set the element to zero. */
                pTetris[i][j] = 0;
            }
        }
        //return pTetris;
    }



	public static void printGrid(byte[][] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			System.out.println(Arrays.toString(grid[i]));
		}
	}

	public int[][] moveShape(int[][] shape, int[] direction) {
		int[][] movedShape = new int[4][2];
		for (int i = 0; i < 4; i++) {
			movedShape[i][0] = (int) (shape[i][0] + direction[0]);
			movedShape[i][1] = (int) (shape[i][1] + direction[1]);
		}
		return movedShape;
	}

	public byte[][] addToGrid(byte[][] grid, int[][] shape, byte current) {
		byte[][] retGrid = deepCopy(grid);
		for (int i = 0; i < 4; i++) {
			retGrid[shape[i][1]][shape[i][0]] = (byte) current;
		}
		return retGrid;
	}

	public int moveDown(byte[][] grid, int[][] shape) {
		int minMove = 1000000;
		for (int i = 0; i < 4; i++) {
			int moveDist = (calcHeight(grid, shape[i][0]) - shape[i][1]);
			if (minMove > moveDist) {
				minMove = moveDist;
			}
		}
		return minMove;
		//return moveShape(shape, new int[] {0, minMove});
	}

	public int calcHeight(byte[][] grid, int x) {
		for (int y = 0; y < ysize; y++) {
			if (grid[y][x] != 0) {
				return (int) (y-1);
			}
		}
		return (int) (ysize-1);
	}

	public int calcHoles(byte[][] grid) {
		int foundHoles = 0;
		for (int x = 0; x < xsize; x++) {
			boolean firstFound = false;
			for (int y = 0; y < ysize; y++) {
				if (grid[y][x] != 0) {
					firstFound = true;
					//System.out.println("finding first");
				} else if (firstFound) {
					foundHoles++;
				}
				if (firstFound) {

					if (x > 0) {
						if (grid[y][x-1] == 0) {
							foundHoles++;
						}
					}
					if (x < xsize-1) {
						if (grid[y][x+1] == 0) {
							foundHoles++;
						}
					}
				}
			}
		}
		return foundHoles;
	}

	public int[] calcMinMaxY(int[][] shape) {
		int minY = shape[0][1];
		int maxY = shape[0][1];
		for (int i = 0; i < 4; i++) {
			if (minY > shape[i][1]) {
				minY = shape[i][1];
			}
			if (maxY < shape[i][1]) {
				maxY = shape[i][1];
			}
		}
		return new int[] {minY, maxY};
	}

	public int[] calcMinMax(int[][] shape) {
		int[] minMax = {shape[0][0], shape[0][0]};
		for (int i = 0; i < 4; i++) {
			if (minMax[0] > shape[i][0]) {
				minMax[0] = shape[i][0];
			}
			if (minMax[1] < shape[i][0]) {
				minMax[1] = shape[i][0];
			}
		}
		return minMax;
	}
}