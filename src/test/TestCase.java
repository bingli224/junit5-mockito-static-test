


import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class TestCase {
	@Test
	public void test ( ) {
		assertTrue(true);

		//assertTrue(false);
	}

	@Test
	public void testMockito_getNewFalse ( ) {
		try (
			MockedStatic<C> c = Mockito.mockStatic(C.class)
		) {
			c.when(() -> C.getTrue()).thenReturn(false);
			assertEquals(false, C.getTrue());
		}

		assertEquals(true, C.getTrue());
	}

	@Test
	public void testMockito_throwException ( ) {
		try (
			MockedStatic<C> c = Mockito.mockStatic(C.class)
		) {
			// reference: https://stackoverflow.com/questions/3762047/throw-checked-exceptions-from-mocks-with-mockito
			//c.when(() -> C.getTrue()).thenThrow(IOException.class);
			c.when(() -> C.getTrue()).thenAnswer( invocation -> {
				throw new IOException();
			} );

			assertThrows(IOException.class, () -> {
				C.getTrue();
			} );
		}

		assertEquals(true, C.getTrue());
	}

	@Test
	public void testMockito_injectException ( ) {
		try (
			MockedStatic<C> c = Mockito.mockStatic(C.class)
		) {
			// reference: https://stackoverflow.com/questions/3762047/throw-checked-exceptions-from-mocks-with-mockito
			//c.when(() -> C.getTrue()).thenThrow(IOException.class);
			c.when(() -> C.getTrue()).thenAnswer( invocation -> {
				throw new IOException();
			} );

			assertThrows(IOException.class, () -> {
				new Holder ( );
			} );
		}

		assertNotNull(new Holder());
	}
}

class Holder {
	public Holder ( ) {
		C.getTrue();
	}
}

class C {
	public static boolean getTrue ( ) {
		return true;
	}
}
