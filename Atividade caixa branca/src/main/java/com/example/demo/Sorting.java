package com.example.demo;

public class Sorting {
    public int[] bubble(int numeros[]) {
		for (int i=0; i < numeros.length; ++i){
            for (int j=i; j < numeros.length; ++j){
                if (numeros[i]>numeros[j]){
                    int aux = numeros[i];
                    numeros[i] = numeros[j];
                    numeros[j] = aux;
                }
            }
        }
        return numeros;
	}

    public int[] insertion(int numeros[]) {
        for (int i = 1; i < numeros.length; i++) {
            int aux = numeros[i];
            int j = i - 1;
            while (j >= 0 && aux < numeros[j]) {
                numeros[j + 1] = numeros[j];
                j--;
            }
            numeros[j + 1] = aux;
        }

        return numeros;
    }

    public int[] quick(int numeros[], int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;
        int mid;
        if (hi0 > lo0) {
            mid = numeros[(hi0 + lo0) / 2];
            while (lo <= hi) {
                while (lo < hi0 && numeros[lo] < mid) {
                    lo++;
                }
                while (hi > lo0 && numeros[hi] > mid) {
                    hi--;
                }
                if (lo <= hi) {
                    int aux = numeros[lo];
                    numeros[lo] = numeros[hi];
                    numeros[hi] = aux;
                    lo++;
                    hi--;
                }
            }
            if (lo0 < hi) {
                quick(numeros, lo0, hi);  
            }
            if (lo < hi0) {
                quick(numeros, lo, hi0);
            }
        }

        return numeros;
    }
}
