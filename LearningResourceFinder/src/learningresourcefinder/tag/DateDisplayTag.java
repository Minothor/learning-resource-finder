package learningresourcefinder.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import learningresourcefinder.util.DateUtil;

public class DateDisplayTag extends SimpleTagSupport{
	
	private Date date;
    private Boolean duration;  // optionnal, means to display "xx days ago" instead of the date, then the date in tooltip.
    private Boolean withspan; 
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

	

    
    public Boolean getDuration() {
        return duration;
    }

    public void setDuration(Boolean duration) {
        this.duration = duration;
    }

    
    public Boolean getWithspan() {
        return withspan;
    }

    public void setWithspan(Boolean withspan) {
        this.withspan = withspan;
    }

    @Override
    public void doTag() throws JspException {
        if (date == null) {
            return;
        }

        String result;
        
        if (duration != null && duration == true) {
            result = DateUtil.formatIntervalFromToNowFR(date);
        } else {
            result = DateUtil.formatyyyyMMdd(date);
        }
        if (withspan== null || withspan){
            result = "<span class='addToolTip' title='"+DateUtil.formatyyyyMMdd(date)+"'>" + result + "</span>";
        } 

        JspWriter out = this.getJspContext().getOut();
        try {
            out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
