package com.theLearningcLub.Model_Class;

/**
 * Created by HP on 5/10/2019.
 */

public class UpstreamModel {

    private  String level,level_agent_id,level_agent_name;

    public UpstreamModel(String level, String level_agent_id, String level_agent_name) {
        this.level = level;
        this.level_agent_id = level_agent_id;
        this.level_agent_name = level_agent_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel_agent_id() {
        return level_agent_id;
    }

    public void setLevel_agent_id(String level_agent_id) {
        this.level_agent_id = level_agent_id;
    }

    public String getLevel_agent_name() {
        return level_agent_name;
    }

    public void setLevel_agent_name(String level_agent_name) {
        this.level_agent_name = level_agent_name;
    }
}
