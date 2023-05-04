package com.nttdata.common.exception;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;


public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("userMessage")
    private String userMessage;

    @JsonProperty("status")
    private BigDecimal status;

    public ErrorResponse errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    /**
     * Get errorCode
     *
     * @return errorCode
     */
    @NotNull

    @Pattern(regexp = "^[a-zA-Z]+$\\d+$")
    @Size(max = 6)
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorResponse userMessage(String userMessage) {
        this.userMessage = userMessage;
        return this;
    }

    /**
     * Get userMessage
     *
     * @return userMessage
     */
    @NotNull

    @Pattern(regexp = "^[a-zA-Z\\s]*$")
    @Size(max = 256)
    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public ErrorResponse status(BigDecimal status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     */

    @Valid
    @Size(max = 15)
    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponse errorResponse = (ErrorResponse) o;
        return Objects.equals(this.errorCode, errorResponse.errorCode) &&
                Objects.equals(this.userMessage, errorResponse.userMessage) &&
                Objects.equals(this.status, errorResponse.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, userMessage, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Error {\n");

        sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
        // sb.append("    developerMessage: ").append(toIndentedString(developerMessage)).append("\n");
        sb.append("    userMessage: ").append(toIndentedString(userMessage)).append("\n");
        // sb.append("    moreInfo: ").append(toIndentedString(moreInfo)).append("\n");
        //  sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
