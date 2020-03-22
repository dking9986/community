package note.dk.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {//页面信息 这一页里面有问题列表 有页面选项
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private  Integer totalPage;

    private Integer page;
    private List<Integer> pages=new ArrayList<>();//展示的总页数 如1 2 3 4 5


    public void setPagination(Integer totalcount, Integer page, Integer size) {//page当前页数 size每页展示多少条数 totalcount总问题条数

        if (totalcount%size==0){
            totalPage=totalcount / size;
        }
        else{
            totalPage=totalcount / size + 1;
        }
        //防止页面溢出
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }

        this.page=page;//把传进来的page参数赋值给这个类的page
        //设置pages 有哪些页数的显示
        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,page-i);//i从头插入
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //是否展示上一页
        if (page==1){
            showPrevious=false;
        } else {
            showPrevious=true;
        }
        //是否展示下一页
        if (page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        //是否展示首页
        if (pages.contains(1)) {
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        //是否展示尾页
        if (pages.contains(totalPage)) {
            showEndPage=false;
        }else {
            showEndPage=true;
        }

    }
}
