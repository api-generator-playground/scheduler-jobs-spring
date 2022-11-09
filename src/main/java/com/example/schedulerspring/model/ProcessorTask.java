package com.example.schedulerspring.model;

import com.example.schedulerspring.service.TestService;
import jdk.internal.loader.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ProcessorTask extends AbstractTask {

    public ProcessorTask(TaskDefinition taskDefinition) {
        super(taskDefinition);
    }

    private String resolvePythonScriptPath(String filename) {
        File file = null;
        try {
            file = new ClassPathResource(String.format("/python_scripts/%s", filename)).getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        File file = new File("python_scripts/" + filename);
        return file.getAbsolutePath();
    }

    private static List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines().collect(Collectors.toList());
        }
    }

    private static void writeProcessInput(OutputStream outputStream, String text) throws IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        writer.write(text);
        writer.flush();
        writer.close();
    }

    private void runPythonFile(String filename) {
        try {
//            Process activateProcess = new ProcessBuilder("/usr/bin/bash", "-c ./env/bin/activate").start();
            ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath(filename));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            writeProcessInput(process.getOutputStream(), "Peter");
            List<String> results = readProcessOutput(process.getInputStream());
            Thread.sleep(2000);
            System.out.println(results);



        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        log.info("[PROCESSOR] Running action: " + taskDefinition.getActionType());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        runPythonFile("hello.py");
        log.info("[PROCESSOR] With Data: " + taskDefinition.getData());
    }
}
