package com.example.gu.four_ten.entity;

import java.util.List;

/**
 * Created by 31452 on 2016/6/19.
 */
public class TextEntity {

    /**
     * result : [{"id":10535,"type":2,"publishtime":636018912000000000,"title":"抬起头来，直视我","summary":"不看到结尾就不会知道故事的全部，感谢作者的投稿。","image":""},{"id":10534,"type":2,"publishtime":636018048000000000,"title":"为什么会有那么久的相恋","summary":"今晚的投稿是在作者生日当天的发到我的邮箱，作者问能否今天发，因为每篇文章都要写摘要，而那天我们的文章已经选好了一篇，所以在那天没有登今天的文章。\r\n\r\n现在回想起来有些歉意，如果文章发表是作者生日愿望，那天我们让她失望了，如果那天晚上她的愿望是可以发表文章，今天他的愿望实现了。\r\n\r\n在今晚咖啡兄住这位美女生日快乐，永远开心，永远20岁！\r\n\r\n投稿后作者会让我给予意见，其实说实话我并没有评论别人作品的能力，我也不是文学家，仅仅是喜好做十个这件事本身，我能看出今晚这篇文字是用心写过，写的时候也都是刻骨铭心的回忆，那些往事虽然时隔很近却历历在目就像发生在昨日，这就足矣！继续写自己内心的文字，晚安！","image":""},{"id":10533,"type":2,"publishtime":636017184000000000,"title":"愿我们没有白白受苦","summary":"勇气都是一时一晃而过，回忆总是那么有意义。","image":""},{"id":10532,"type":2,"publishtime":636016320000000000,"title":"那一抹流光","summary":"感谢作者授权发表。\r\n\r\n高考结束，在估分报志愿的阶段，回忆一下青春高中是我最难忘最难忘的一个经历。有梦想，有斗志，有青春，有想念的人。","image":""},{"id":10531,"type":2,"publishtime":636015456000000000,"title":"我的写作秘诀无私分享","summary":"今晚的文章略带调侃，但细细品味也有道理。","image":""},{"id":10530,"type":2,"publishtime":636014592000000000,"title":"父子书","summary":"来看一看我们和下一代的差别。","image":""},{"id":10529,"type":2,"publishtime":636013728000000000,"title":"江湖在我笔下，佳人在我文章里","summary":"我有一枝笔，能写能浪能纵横","image":""},{"id":10528,"type":2,"publishtime":636012864000000000,"title":" 异次爱情","summary":"感谢作者的投稿，不知道今晚的爱情是什么样？","image":""},{"id":10527,"type":2,"publishtime":636012000000000000,"title":"我的高考","summary":"高考已经结束，都在估分报志愿，希望每个人都考上自己理想大学。\r\n\r\n回想高考每个人心有戚戚焉。","image":""},{"id":10526,"type":2,"publishtime":636011136000000000,"title":"不配","summary":"我抱他，好像一切都来得及，好像我还有踮脚的余地。","image":""}]
     * status : 0
     * errMsg : null
     */

    private int status;
    private Object errMsg;
    /**
     * id : 10535
     * type : 2
     * publishtime : 636018912000000000
     * title : 抬起头来，直视我
     * summary : 不看到结尾就不会知道故事的全部，感谢作者的投稿。
     * image :
     */

    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private int id;
        private int type;
        private long publishtime;
        private String title;
        private String summary;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(long publishtime) {
            this.publishtime = publishtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
