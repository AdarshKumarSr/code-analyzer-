package com.codeanalyzer.code_analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/analyze")
public class AnalyzerController {

    @PostMapping
    public ResponseEntity<?> analyzeCode(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("upload-", ".java");
            file.transferTo(tempFile);

            return runPmdAnalysis(tempFile.getAbsolutePath());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/github")
    public ResponseEntity<?> analyzeGitHubRepo(@RequestParam("repoUrl") String repoUrl) {
        try {
            File tempDir = File.createTempFile("repo-", "");
            tempDir.delete();
            tempDir.mkdir();

            ProcessBuilder gitCloneBuilder = new ProcessBuilder("git", "clone", repoUrl, tempDir.getAbsolutePath());
            gitCloneBuilder.redirectErrorStream(true);
            Process gitProcess = gitCloneBuilder.start();

            BufferedReader gitReader = new BufferedReader(new InputStreamReader(gitProcess.getInputStream()));
            while (gitReader.readLine() != null);  // Read output, optionally log it

            if (gitProcess.waitFor() != 0) {
                return ResponseEntity.status(500).body(Map.of("error", "Failed to clone repository"));
            }

            return runPmdAnalysis(tempDir.getAbsolutePath());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    private ResponseEntity<?> runPmdAnalysis(String path) {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                "C:\\Users\\Adarsh\\OneDrive\\Desktop\\pmd-dist-7.14.0-bin\\pmd-bin-7.14.0\\bin\\pmd.bat",
                "check",
                "-d", path,
                "-R", "category/java/bestpractices.xml",
                "-f", "text"
            );

            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<Map<String, Object>> issues = new ArrayList<>();

            Pattern pattern = Pattern.compile(".*:(\\d+):\\s*(\\w+):\\s*(.+)");

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    Map<String, Object> issue = new HashMap<>();
                    issue.put("line", Integer.parseInt(matcher.group(1)));
                    issue.put("rule", matcher.group(2));
                    issue.put("message", matcher.group(3));
                    issues.add(issue);
                }
            }

            return ResponseEntity.ok(Map.of("issues", issues));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
