package md.rosca;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TimeEntry {
    @NonNull
    private String project;
    private String client;
    @NonNull
    private String description;
    @NonNull
    private String duration; //In format [HH]:MM:SS
}