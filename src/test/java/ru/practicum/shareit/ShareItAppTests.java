package ru.practicum.shareit;
/*
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
 */
class ShareItAppTests {
	/*
	private final ItemController itemController;
	private final UserController userController;

	private final User user1 = new User("Пользователь 1", "email1@ya.ru");
	private final User user2 = new User("Пользователь 2", "email2@ya.ru");
	private final User user3 = new User("Пользователь 3", "email3@ya.ru");

	private final Item item1 = new Item("Предмет 1", "Описание 1", true);
	private final Item item2 = new Item("Предмет 2", "Описание 2", true);
	private final Item item3 = new Item("Предмет 3", "Описание 3", true);

	@Test
	@DirtiesContext
	void shouldAddUser() {
		UserDto testUser = userController.addUser(user1);
		assertEquals(1, testUser.getId());
		assertEquals("Пользователь 1", testUser.getName());
		assertEquals("email1@ya.ru", testUser.getEmail());
	}

	@Test
	@DirtiesContext
	 void shouldUpdateUser() {
		createTestUsers();
		UserDto testUser = userController.updateUser(2L, new User(null, "newemail2@ya.ru"));
		assertEquals(2, testUser.getId());
		assertEquals("Пользователь 2", testUser.getName());
		assertEquals("newemail2@ya.ru", testUser.getEmail());
	}

	@Test
	@DirtiesContext
	 void shouldDeleteUser() {
		createTestUsers();
		userController.deleteUser(2L);
		assertThrows(NullPointerException.class,() -> userController.getUser(2L));
	}

	@Test
	@DirtiesContext
	 void shouldGetUser() {
		createTestUsers();
		UserDto testUser = userController.getUser(2L);
		assertEquals(2, testUser.getId());
		assertEquals("Пользователь 2", testUser.getName());
		assertEquals("email2@ya.ru", testUser.getEmail());
	}

	@Test
	@DirtiesContext
	 void shouldGetUsers() {
		createTestUsers();
		UserDto testUser1 = UserMapper.toUserDto(user1);
		testUser1.setId(1L);
		UserDto testUser2 = UserMapper.toUserDto(user2);
		testUser2.setId(2L);
		UserDto testUser3 = UserMapper.toUserDto(user3);
		testUser3.setId(3L);
		List<UserDto> testUserList = userController.getUsers();
		assertTrue(testUserList.contains(testUser1));
		assertTrue(testUserList.contains(testUser2));
		assertTrue(testUserList.contains(testUser3));
	}

	@Test
	@DirtiesContext
	 void shouldAddItem() {
		createTestUsers();
		ItemDto testItem = itemController.addItem(item1, 1L);
		assertEquals(1L, testItem.getId());
		assertEquals("Название 1", testItem.getName());
		assertEquals("Описание 1", testItem.getDescription());
	}

	@Test
	@DirtiesContext
	 void shouldUpdateItem() {
		createTestUsers();
		createTestItems();
		ItemDto testItem = itemController.updateItem(2L, new Item(null,
				"Новое описание 2", null), 2L);
		assertEquals(2, testItem.getId());
		assertEquals("Предмет 2", testItem.getName());
		assertEquals("Новое описание 2", testItem.getDescription());
		assertEquals(true, testItem.getAvailable());
	}

	@Test
	@DirtiesContext
	 void shouldDeleteItem() {
		createTestUsers();
		createTestItems();
		itemController.deleteItem(2L, 2L);
		assertThrows(NullPointerException.class,() -> itemController.getItem(2L));
	}

	@Test
	@DirtiesContext
	 void shouldGetItem() {
		createTestUsers();
		createTestItems();
		ItemDto testItem = itemController.getItem(2L);
		assertEquals(2, testItem.getId());
		assertEquals("Предмет 2", testItem.getName());
		assertEquals("Описание 2", testItem.getDescription());
		assertEquals(true, testItem.getAvailable());
	}

	@Test
	@DirtiesContext
	 void shouldGetItemsByName() {
		createTestUsers();
		createTestItems();
		itemController.updateItem(1L, new Item("Новый предмет", null, false), 1L);
		itemController.updateItem(3L, new Item(null, "Новое описание", true), 3L);
		List<ItemDto> testItemList = itemController.getItemsByName("нОВ");
		assertEquals(1, testItemList.size());
		assertEquals(3L, testItemList.get(0).getId());
		assertEquals("Предмет 3", testItemList.get(0).getName());
		assertEquals("Новое описание 3", testItemList.get(0).getDescription());
		assertEquals(true, testItemList.get(0).getAvailable());
	}

	@Test
	@DirtiesContext
	 void shouldGetItemsByOwner() {
		createTestUsers();
		createTestItems();
		ItemDto testItem2 = new ItemDto(2L, "Предмет 2", "Описание 2", true);
		ItemDto testItem4 = new ItemDto(4L, "Предмет 4", "Описание 4", true);
		itemController.addItem(new Item("Предмет 4", "Описание 4", true), 2L);
		List<ItemDto> testItemList = itemController.getItemsByOwner(2L);
		assertEquals(2, testItemList.size());
		assertTrue(testItemList.contains(testItem2));
		assertTrue(testItemList.contains(testItem4));
	}

	 void createTestUsers() {
		userController.addUser(user1);
		userController.addUser(user2);
		userController.addUser(user3);
	}

	 void createTestItems() {
		itemController.addItem(item1, 1L);
		itemController.addItem(item2, 2L);
	 	itemController.addItem(item3, 3L);
	}
	 */
}
