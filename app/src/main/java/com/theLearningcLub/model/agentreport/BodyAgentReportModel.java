
package com.theLearningcLub.model.agentreport;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BodyAgentReportModel {

    @SerializedName("level")
    private String mLevel;
    @SerializedName("level_agent_id")
    private String mLevelAgentId;
    @SerializedName("level_agent_name")
    private String mLevelAgentName;

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        mLevel = level;
    }

    public String getLevelAgentId() {
        return mLevelAgentId;
    }

    public void setLevelAgentId(String levelAgentId) {
        mLevelAgentId = levelAgentId;
    }

    public String getLevelAgentName() {
        return mLevelAgentName;
    }

    public void setLevelAgentName(String levelAgentName) {
        mLevelAgentName = levelAgentName;
    }

}
