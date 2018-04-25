package org.smartloli.kafka.eagle.web.utils;

/**
 * Created by dujijun on 2018/3/27.
 */
public class ValidateResult {
    public enum ResultCode{
        SUCCESS("成功"), FAILURE("失败"), WARNING("警告");
        String mes;
        ResultCode(String mes){
            this.mes = mes;
        }
    }

    private ResultCode resultCode;
    private String mes;
    private Object attach;

    public ValidateResult(ResultCode resultCode, String mes) {
        this.resultCode = resultCode;
        this.mes = mes;
    }

    public ValidateResult(ResultCode resultCode, String mes, Object attach) {
        this.resultCode = resultCode;
        this.mes = mes;
        this.attach = attach;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Object getAttach() {
        return attach;
    }

    public void setAttach(Object attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return resultCode.mes + ": " + mes + (attach == null ? "" : attach.toString());
    }
}