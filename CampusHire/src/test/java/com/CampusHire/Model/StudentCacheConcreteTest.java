package com.CampusHire.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentCacheConcreteTest {

    private StudentCacheConcrete<Long, Student> cache;

    @BeforeEach
    void setUp() {
        cache = new StudentCacheConcrete<>();
    }

    @Test
    void testInitialState() {
        // Test initial cache state
        assertEquals(0, cache.size());
        assertFalse(cache.containsKey(1L));
        assertNull(cache.get(1L));
    }

    @Test
    void testPutAndGet() {
        // Test basic put and get operations
        Student student1 = new Student(1L, "John Doe", "123456789", "john@email.com", "Computer Science", 22, 2024);
        Student student2 = new Student(2L, "Jane Smith", "987654321", "jane@email.com", "Mathematics", 21, 2024);

        cache.put(1L, student1);
        cache.put(2L, student2);

        assertEquals(2, cache.size());
        assertTrue(cache.containsKey(1L));
        assertTrue(cache.containsKey(2L));
        assertEquals(student1, cache.get(1L));
        assertEquals(student2, cache.get(2L));
    }

    @Test
    void testLRUEviction() {
        // Test LRU eviction when cache reaches capacity (3 items)
        Student student1 = new Student(1L, "John Doe", "123456789", "john@email.com", "Computer Science", 22, 2024);
        Student student2 = new Student(2L, "Jane Smith", "987654321", "jane@email.com", "Mathematics", 21, 2024);
        Student student3 = new Student(3L, "Bob Johnson", "555555555", "bob@email.com", "Physics", 23, 2024);
        Student student4 = new Student(4L, "Alice Brown", "111111111", "alice@email.com", "Chemistry", 20, 2024);

        // Add first 3 students
        cache.put(1L, student1);
        cache.put(2L, student2);
        cache.put(3L, student3);

        assertEquals(3, cache.size());
        assertTrue(cache.containsKey(1L));
        assertTrue(cache.containsKey(2L));
        assertTrue(cache.containsKey(3L));

        // Add 4th student - should evict the least recently used (student1)
        cache.put(4L, student4);

        assertEquals(3, cache.size());
        assertFalse(cache.containsKey(1L)); // student1 should be evicted
        assertTrue(cache.containsKey(2L));
        assertTrue(cache.containsKey(3L));
        assertTrue(cache.containsKey(4L));
    }

    @Test
    void testLRUAccessPattern() {
        // Test that accessing an item makes it most recently used
        Student student1 = new Student(1L, "John Doe", "123456789", "john@email.com", "Computer Science", 22, 2024);
        Student student2 = new Student(2L, "Jane Smith", "987654321", "jane@email.com", "Mathematics", 21, 2024);
        Student student3 = new Student(3L, "Bob Johnson", "555555555", "bob@email.com", "Physics", 23, 2024);
        Student student4 = new Student(4L, "Alice Brown", "111111111", "alice@email.com", "Chemistry", 20, 2024);

        // Add 3 students
        cache.put(1L, student1);
        cache.put(2L, student2);
        cache.put(3L, student3);

        // Access student1 (should make it most recently used)
        cache.get(1L);

        // Add 4th student - should evict student2 (least recently used after accessing student1)
        cache.put(4L, student4);

        assertEquals(3, cache.size());
        assertTrue(cache.containsKey(1L)); // student1 should remain (was accessed)
        assertFalse(cache.containsKey(2L)); // student2 should be evicted
        assertTrue(cache.containsKey(3L));
        assertTrue(cache.containsKey(4L));
    }

    @Test
    void testContainsKey() {
        // Test containsKey method
        Student student = new Student(1L, "John Doe", "123456789", "john@email.com", "Computer Science", 22, 2024);
        
        assertFalse(cache.containsKey(1L));
        
        cache.put(1L, student);
        assertTrue(cache.containsKey(1L));
        
        // Test with non-existent key
        assertFalse(cache.containsKey(999L));
    }

    @Test
    void testGetNonExistentKey() {
        // Test getting a key that doesn't exist
        assertNull(cache.get(999L));
    }

    @Test
    void testMultipleAccesses() {
        // Test multiple accesses to the same key
        Student student = new Student(1L, "John Doe", "123456789", "john@email.com", "Computer Science", 22, 2024);
        
        cache.put(1L, student);
        
        // Access the same student multiple times
        Student retrieved1 = cache.get(1L);
        Student retrieved2 = cache.get(1L);
        Student retrieved3 = cache.get(1L);
        
        assertEquals(student, retrieved1);
        assertEquals(student, retrieved2);
        assertEquals(student, retrieved3);
        assertEquals(1, cache.size());
    }

    @Test
    void testCacheCapacityLimit() {
        // Test that cache never exceeds capacity of 3
        Student student1 = new Student(1L, "John Doe", "123456789", "john@email.com", "Computer Science", 22, 2024);
        Student student2 = new Student(2L, "Jane Smith", "987654321", "jane@email.com", "Mathematics", 21, 2024);
        Student student3 = new Student(3L, "Bob Johnson", "555555555", "bob@email.com", "Physics", 23, 2024);
        Student student4 = new Student(4L, "Alice Brown", "111111111", "alice@email.com", "Chemistry", 20, 2024);
        Student student5 = new Student(5L, "Charlie Wilson", "222222222", "charlie@email.com", "Biology", 24, 2024);

        cache.put(1L, student1);
        cache.put(2L, student2);
        cache.put(3L, student3);
        assertEquals(3, cache.size());

        cache.put(4L, student4);
        assertEquals(3, cache.size());

        cache.put(5L, student5);
        assertEquals(3, cache.size());
    }

    @Test
    void testUpdateExistingKey() {
        // Test updating an existing key
        Student student1 = new Student(1L, "John Doe", "123456789", "john@email.com", "Computer Science", 22, 2024);
        Student student1Updated = new Student(1L, "John Doe Updated", "123456789", "john.updated@email.com", "Computer Science", 23, 2024);

        cache.put(1L, student1);
        assertEquals(student1, cache.get(1L));

        cache.put(1L, student1Updated);
        assertEquals(student1Updated, cache.get(1L));
        assertEquals(1, cache.size()); // Size should remain 1
    }

    @Test
    void testComplexLRUScenario() {
        // Test a more complex LRU scenario
        Student student1 = new Student(1L, "John Doe", "123456789", "john@email.com", "Computer Science", 22, 2024);
        Student student2 = new Student(2L, "Jane Smith", "987654321", "jane@email.com", "Mathematics", 21, 2024);
        Student student3 = new Student(3L, "Bob Johnson", "555555555", "bob@email.com", "Physics", 23, 2024);
        Student student4 = new Student(4L, "Alice Brown", "111111111", "alice@email.com", "Chemistry", 20, 2024);

        // Fill cache
        cache.put(1L, student1);
        cache.put(2L, student2);
        cache.put(3L, student3);

        // Access student2, then student1
        cache.get(2L);
        cache.get(1L);

        // Add new student - should evict student3 (least recently used)
        cache.put(4L, student4);

        assertEquals(3, cache.size());
        assertTrue(cache.containsKey(1L));
        assertTrue(cache.containsKey(2L));
        assertFalse(cache.containsKey(3L)); // Should be evicted
        assertTrue(cache.containsKey(4L));
    }
} 