package com.apashkevich.epam;

import static com.apashkevich.epam.Java8WorkingWithFiles1.HOME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class Java8WorkingWithFiles3 {


    @Test
    public void givenPath_whenDeletes_thenCorrect() throws IOException {
        Path p = Paths.get(HOME + "/fileToDelete.txt");
        assertFalse(Files.exists(p));
        Files.createFile(p);
        assertTrue(Files.exists(p));

        Files.delete(p);

        assertFalse(Files.exists(p));
    }

    @Test
    public void givenInexistentFile_whenDeleteIfExistsWorks_thenCorrect() throws IOException {
        Path p = Paths.get(HOME + "/inexistentFile.txt");
        assertFalse(Files.exists(p));

        Files.deleteIfExists(p);
    }

    @Test
    public void givenPath_whenFailsToDeleteNonEmptyDir_thenCorrect() throws IOException {
        Path dir = Paths.get(
            HOME + "/emptyDir" + UUID.randomUUID().toString());
        Files.createDirectory(dir);
        assertTrue(Files.exists(dir));

        Path file = dir.resolve("file.txt");
        Files.createFile(file);

        assertThrows(DirectoryNotEmptyException.class, () -> Files.delete(dir));

        assertTrue(Files.exists(dir));
    }

    @Test
    public void givenFilePath_whenCopiesToNewLocation_thenCorrect() throws IOException {
        Path dir1 = Paths.get(
            HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(
            HOME + "/otherdir_" + UUID.randomUUID().toString());

        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");

        Files.createFile(file1);

        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));

        Files.copy(file1, file2);

        assertTrue(Files.exists(file2));
    }

    @Test
    public void givenPath_whenCopyFailsDueToExistingFile_thenCorrect() throws IOException {
        Path dir1 = Paths.get(
            HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(
            HOME + "/otherdir_" + UUID.randomUUID().toString());

        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");

        Files.createFile(file1);
        Files.createFile(file2);

        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));

        assertThrows(FileAlreadyExistsException.class, () ->  Files.copy(file1, file2));


        Files.copy(file1, file2, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void givenFilePath_whenMovesToNewLocation_thenCorrect() throws IOException {
        Path dir1 = Paths.get(
            HOME + "/firstdir_" + UUID.randomUUID().toString());
        Path dir2 = Paths.get(
            HOME + "/otherdir_" + UUID.randomUUID().toString());

        Files.createDirectory(dir1);
        Files.createDirectory(dir2);

        Path file1 = dir1.resolve("filetocopy.txt");
        Path file2 = dir2.resolve("filetocopy.txt");
        Files.createFile(file1);

        assertTrue(Files.exists(file1));
        assertFalse(Files.exists(file2));

        Files.move(file1, file2);

        assertTrue(Files.exists(file2));
        assertFalse(Files.exists(file1));
    }

}
