package com.qf.miaosha;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaApplicationTests {

	@Test
	public void contextLoads() {
		int[] b = {8,2,7,4,5};
		for(int i=0;i<b.length-1;i++){
			for (int j=0;j<b.length-i-1;j++){
				if(b[j]>b[j+1]) {
					int temp = b[j];
					b[j]=b[j+1];
					b[j+1]=temp;
				}
			}
		}
		for (int i : b) {
			System.out.println(i);
		}

	}

}
