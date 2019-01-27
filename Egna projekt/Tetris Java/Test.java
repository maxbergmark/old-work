import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;

public class Test {
	int[] setOnes;
	int ysize = 5;
	int xsize = 10;


	public Test() {
		byte[][] grid = {{1, 0, 1} ,{2, 2, 2}, {3, 3, 3}, {4, 4, 4}, {5, 5, 5}, {6, 0, 6}, {7, 0, 7}, {8, 0, 8}};
		int[] grid2 = {0b1111111111, 
					   0b1111111111, 
					   0b1111111111, 
					   0b1111111111, 
					   0b1111111110};

		setOnes = new int[1<<xsize];
		for (int i = 0; i < 1<<xsize; i++) {
			setOnes[i] = popcount(i);
		}

		printConverted(grid2);
		//int[] rows = {1, 3, 5, 6, 7};
		int[] rows = {5};
		//printGrid(grid);
		long time1 = System.nanoTime();
		for (int i = 0; i < 1; i++) {
			int newg = calcHolesConverted(grid2);
			int newg2 = calcHolesConverted2(grid2);
			//byte[][] newGrid = checkRows(grid, new int[] {1, 4});
			//byte[][] newGrid = onClearFilledRows(grid);
			//printGrid(newGrid);
			System.out.println(newg + "   " + newg2);
		}
		System.out.println(32<<-1);
		long time2 = System.nanoTime();
		System.out.println(time2-time1);
		//printGrid(newGrid);
	}

	public int popcount(int v) {
	    v = v - ((v >> 1) & 0x55555555);                // put count of each 2 bits into those 2 bits
	    v = (v & 0x33333333) + ((v >> 2) & 0x33333333); // put count of each 4 bits into those 4 bits  
	    return v = ((v + (v >> 4) & 0xF0F0F0F) * 0x1010101) >> 24;
	}

	public int totalHeight(int[] grid) {
		int maxHeight = ysize;
		for (int x = 0; x < xsize; x++) {
			if (maxHeight > calcHeight(grid, x)) {
				maxHeight = calcHeight(grid, x);
			}
		}
		return maxHeight;
	}

	public int calcHolesConverted4(int[] grid) {
		int foundHoles = 0;
		int minY = 0;
		while ( minY < ysize && grid[minY] == EMPTY_ROW ) {
        	minY++;
    	}
		for (int x = 0; x < xsize; x++) {
			int y = minY;
			for ( ; y < ysize; y++) {

				if (((grid[y]) & 1<<x) == 0) {
					break;	
						//foundHoles += setOnes[(grid[y] >> (x-1)) & 7];
				}
			}


			for ( ; y < ysize; y++) {
				//foundHoles += (x > 0) ? setOnes[grid[y] & 7<<(x-1)] : setOnes[grid[y] & 3];
				foundHoles += setOnes[grid[y]<<1 & 7<<x];
			}
			
		}
		return foundHoles;
	}

	public int calcHolesConverted3(int[] grid) {
		int foundHoles = 0;
		int minY = 0;
		while ( minY < ysize && grid[minY] == EMPTY_ROW ) {
        	minY++;
    	}
		for (int x = 0; x < xsize; x++) {
			boolean firstFound = false;
			for (int y = minY; y < ysize; y++) {
				if (!firstFound) {
					if (((grid[y]) & 1<<x) == 0) {
						firstFound = true;
						//foundHoles += setOnes[(grid[y] >> (x-1)) & 7];
					}

				} else {
					//foundHoles += (grid[y] >> (x-1)) & 1;
					//foundHoles += (grid[y] >> (x+1)) & 1;
					//foundHoles += setOnes[(grid[y] >> (x-1)) & 7];
					foundHoles += (x > 0) ? setOnes[grid[y] & 7<<(x-1)] : setOnes[grid[y] & 3];
				}
			}
		}
		return foundHoles;
	}

	public int calcHolesConverted(int[] grid) {
		int foundHoles = 0;
		for (int x = 0; x < xsize; x++) {
			boolean firstFound = false;
			for (int y = 0; y < ysize; y++) {
				if (!firstFound) {
					if (((grid[y] >> x) & 1) == 0) {
						firstFound = true;
						foundHoles += (x > 0) ? setOnes[(grid[y] >> (x-1)) & 7] : setOnes[(grid[y] << 1) & 7];
					}

				} else {
					//foundHoles += (grid[y] >> (x-1)) & 1;
					//foundHoles += (grid[y] >> (x+1)) & 1;
					foundHoles += setOnes[(grid[y] >> (x-1)) & 7];
				}
			}
		}
		return foundHoles;
	}

	public int calcHolesConverted2(int[] grid) {
		int foundHoles = 0;
		for (int x = xsize-1; x >= 0; x--) {
			boolean firstFound = false;
			for (int y = 0; y < ysize; y++) {
				if (((grid[y] >> x) & 1) == 0) {
					firstFound = true;
				} else if (firstFound) {
					foundHoles++;
				}
				if (firstFound) {
					foundHoles += (grid[y] >> (x-1)) & (x > 0 ? 1 : 0);
					foundHoles += (grid[y] >> (x+1)) & (x < xsize-1 ? 1 : 0);
				}
			}
		}
		return foundHoles;
	}

	public int[] checkRowsConverted(int[] grid, int[] bounds) {
		int[] retGrid = new int[grid.length];
		System.arraycopy(grid, bounds[1]+1, retGrid, bounds[1]+1, grid.length-bounds[1]-1);
		int found = 0;
		for (int i = bounds[1]; i >= bounds[0]; i--) {
			printConverted(retGrid);
			if (grid[i] == 0) {
				retGrid[i] = grid[i-(++found)];
			} else {
				retGrid[i] = grid[i-found];
			}
		}
		for (int i = bounds[0]-1; i >= 0; i--) {
			retGrid[i] = 1023;
		}
		return retGrid;
	}

	private static final byte[][] onRemoveRowsWithNoZeroes(final byte[][] pTetrisTable, final int pRowLength) {
        /* Declare iteration variables. */
        int j = 0, i = 0;
        /* Track the row length of the TetrisTable. */
        int lTableLength = pTetrisTable.length;
        /* Iterate through each row of the TetrisTable. */
        for(i = 0; i < pTetrisTable.length; i++) {
            /* Declare a boolean to track whether the current row posseses zeros. */
            boolean lHasZeroes = false;
            for(j = 0; j < pRowLength; j++) {
                /* Iteratively check that each cell to see whether it contains a zero.  */
                lHasZeroes |= (pTetrisTable[i][j] == 0);
            }
            /* Determine if the current row didn't end up containing any zeros.  */
            if(!lHasZeroes) {
                /* If there are other rows to follow... */
                for(j = i; j < pTetrisTable.length - 1; j++) {
                    /* Move these rows downwards. (System.arraycopy() is blazing fast.) */
                    System.arraycopy(pTetrisTable[j + 1], 0, pTetrisTable[j], 0, pTetrisTable[j].length);
                }
                /* Decrement the row counter. (We'll need to re-test this position.) */
                if(j > i) { i--; }
                /* Decrement the TableLength. */
                lTableLength--;
            }
        }
        /* Determine if the table has been modified. */
        if(lTableLength != pTetrisTable.length) {
            /* Copy the usable portion of the table into a new array. */
            final byte[][] lReducedTable = new byte[pTetrisTable.length][pRowLength];
            /* Replace the data. */
            for(i = 0; i < lReducedTable.length; i++) {
                System.arraycopy(pTetrisTable[i], 0, lReducedTable[i], 0, pTetrisTable[i].length);
            }
            /* Return the resized table. */
            System.out.println("returning reduced");
            return lReducedTable;
        }
        /* If the table hasn't been modified, then every row contained a zero. Return the TetrisTable unharmed. */
        return pTetrisTable;
    }

   	private static final byte[][] onClearFilledRows(final byte[][] pTetris) {
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
            if(lContainsZero) {
                System.arraycopy(pTetris[i], 0, pTetris[lLastValidIndex--], 0, pTetris[i].length);
            }
        }
        /* Empty the remaining rows. */
        for(i = lLastValidIndex; i >= 0; i--) {
            for(j = 0; j < pTetris[i].length; j++) {
                /* Set the element to zero. */
                pTetris[i][j] = 0;
            }
        }
        return pTetris;
    }

	public byte[][] checkRows(byte[][] grid, int[] bounds) {
		LinkedList<Integer> temp = new LinkedList<Integer>();
		boolean willRemove = false;
		for (int y = bounds[0]; y < bounds[1]+1; y++) {
			boolean tempCheck = true;
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x] == 0) {
					tempCheck = false;
					break;
				}
			}
			if (tempCheck) {
				willRemove = true;
				temp.add(y);
			}
		}
		
		if (willRemove) {
			int[] rowArray = convert(temp);
			return removeRows(grid, rowArray);
		}
		return grid;
		
	}

	public int[] convert(LinkedList<Integer> intList) {
		int[] retArray = new int[intList.size()];
		for (int i = 0; i < retArray.length; i++) {
			retArray[i] = intList.get(i).intValue();
		}
		return retArray;
	}


	public byte[][] removeRows(byte[][] grid, int[] rows) {

		int total = rows.length;
		int current = 0;
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = total; i < grid.length; i++) {
			if (current < total && i-total+current == rows[current]) {
				current++;
			}
			//retGrid[i] = grid[i-total+current].clone();
			System.arraycopy(grid[i-total+current], 0, retGrid[i], 0, grid[0].length);
		}
		return retGrid;
	}

	public byte[][] removeRow(byte[][] grid, int row) {
		byte[][] retGrid = new byte[grid.length][grid[0].length];
		for (int i = 1; i < grid.length; i++) {
			if (i <= row) {
				retGrid[i] = grid[i-1].clone();
			} else {
				retGrid[i] = grid[i].clone();
			}
		}
		return retGrid;
	}

	public void printGrid(byte[][] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			System.out.println(Arrays.toString(grid[i]));
		}
	}

	public void printConverted(int[] grid) {
		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			//System.out.println(Integer.toString(grid[i], 2));
			System.out.println(String.format("%" + String.valueOf(xsize) + "s", Integer.toBinaryString(grid[i])).replace(' ', '0'));
		}
	}

	public static void main(String[] args) {
		new Test();
	}
}