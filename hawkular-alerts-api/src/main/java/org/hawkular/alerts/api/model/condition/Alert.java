/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.alerts.api.model.condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawkular.alerts.api.model.Severity;
import org.hawkular.alerts.api.model.dampening.Dampening;
import org.hawkular.alerts.api.model.trigger.Trigger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A status of an alert thrown by several matched conditions.
 *
 * @author Jay Shaughnessy
 * @author Lucas Ponce
 */
public class Alert {

    /**
     * Used to annotate fields that should be thinned in order to return/deserialize a lightweight Alert
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Thin {
    }

    public enum Status {
        OPEN, ACKNOWLEDGED, RESOLVED
    };

    @JsonInclude
    private String tenantId;

    // This is a generated composite of form: triggerId|ctime
    @JsonInclude
    private String alertId;

    @JsonInclude
    private String triggerId;

    @JsonInclude
    private long ctime;

    @JsonInclude(Include.NON_EMPTY)
    @Thin
    private List<Set<ConditionEval>> evalSets;

    @JsonInclude
    private Severity severity;

    @JsonInclude
    private Status status;

    @JsonInclude
    private long ackTime;

    @JsonInclude
    private String ackBy;

    @JsonInclude
    private String ackNotes;

    @JsonInclude
    private long resolvedTime;

    @JsonInclude
    private String resolvedBy;

    @JsonInclude
    private String resolvedNotes;

    /*
     * If set this should be the trigger as defined when the alert was fired.  A trigger definition can change
     * over time, but an alert should be attached with the relevant instance.
     */
    @JsonInclude(Include.NON_EMPTY)
    @Thin
    private Trigger trigger;

    /*
     * This is the dampening attached to a trigger when the alert was fired.
     * As a trigger, the dampening can change during time, but an alert should be attached with a specific instance.
     */
    @JsonInclude(Include.NON_EMPTY)
    @Thin
    private Dampening dampening;

    @JsonInclude(Include.NON_EMPTY)
    @Thin
    private List<Set<ConditionEval>> resolvedEvalSets;

    /*
     * This should be initialized to the owning trigger's context. It is not set automatically so as to allow
     * for flexibility.  Note, this is not marked as Thin, whereas the trigger is Thin.
     */
    @JsonInclude(Include.NON_EMPTY)
    private Map<String, String> context;

    public Alert() {
        // for json assembly
    }

    public Alert(String tenantId, String triggerId, Severity severity, List<Set<ConditionEval>> evalSets) {
        this.tenantId = tenantId;
        this.triggerId = triggerId;
        this.severity = (null == severity) ? Severity.MEDIUM : severity;
        this.evalSets = evalSets;

        this.ctime = System.currentTimeMillis();
        this.status = Status.OPEN;

        this.alertId = triggerId + "-" + ctime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public List<Set<ConditionEval>> getEvalSets() {
        return evalSets;
    }

    public void setEvalSets(List<Set<ConditionEval>> evalSets) {
        this.evalSets = evalSets;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getAckTime() {
        return ackTime;
    }

    public void setAckTime(long ackTime) {
        this.ackTime = ackTime;
    }

    public String getAckBy() {
        return ackBy;
    }

    public void setAckBy(String ackBy) {
        this.ackBy = ackBy;
    }

    public String getAckNotes() {
        return ackNotes;
    }

    public void setAckNotes(String ackNotes) {
        this.ackNotes = ackNotes;
    }

    public long getResolvedTime() {
        return resolvedTime;
    }

    public void setResolvedTime(long resolvedTime) {
        this.resolvedTime = resolvedTime;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public String getResolvedNotes() {
        return resolvedNotes;
    }

    public void setResolvedNotes(String resolvedNotes) {
        this.resolvedNotes = resolvedNotes;
    }

    public List<Set<ConditionEval>> getResolvedEvalSets() {
        return resolvedEvalSets;
    }

    public void setResolvedEvalSets(List<Set<ConditionEval>> resolvedEvalSets) {
        this.resolvedEvalSets = resolvedEvalSets;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public Dampening getDampening() {
        return dampening;
    }

    public void setDampening(Dampening dampening) {
        this.dampening = dampening;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    /**
     * Add context information.
     * @param name context key.
     * @param value context value.
     */
    public void addProperty(String name, String value) {
        if (null == name || null == value) {
            throw new IllegalArgumentException("Propety must have non-null name and value");
        }
        if (null == context) {
            context = new HashMap<>();
        }
        context.put(name, value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alertId == null) ? 0 : alertId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alert other = (Alert) obj;
        if (alertId == null) {
            if (other.alertId != null)
                return false;
        } else if (!alertId.equals(other.alertId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Alert [alertId=" + alertId + ", status=" + status + ", ackTime=" + ackTime
                + ", ackBy=" + ackBy + ", resolvedTime=" + resolvedTime + ", resolvedBy=" + resolvedBy + ", context="
                + context + "]";
    }

}
