package Model;

public class Income
{

    int userId;
    int logId;
    int amount;
    String type;
    String note;
    String date;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getLogId()
    {
        return logId;
    }

    public void setLogId(int logId)
    {
        this.logId = logId;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Income(int userId, int logId, int amount, String type, String note, String date)
    {
        this.userId = userId;
        this.logId = logId;
        this.amount = amount;
        this.type = type;
        this.note = note;
        this.date = date;

    }
}

