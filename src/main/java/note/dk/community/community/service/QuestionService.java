package note.dk.community.community.service;

import note.dk.community.community.dto.PaginationDTO;
import note.dk.community.community.dto.QuestionDTO;
import note.dk.community.community.mapper.QuestionMapper;
import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.Question;
import note.dk.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service//起到组装作用 将questionmapper和usermapper组装起来
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalcount=questionMapper.count();//总问题条数
        paginationDTO.setPagination(totalcount,page,size);//给paginationDTO里面除了question表以外的设置好 然后返回出去
        if (page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        //上面的代码写在前面能保证page不会溢出
        Integer offset=size*(page-1);//page页码 size每页个数 offset是显示的开始位置
        List<Question> questions=questionMapper.list(offset,size);//question列表 用于显示对应个数的问题的列表
        List<QuestionDTO> questionDTOS=new ArrayList<>();//dto列表用于存到前端去的列表 比上面question多了user信息

        for (Question question : questions) {
            User user=userMapper.findByAccountId(question.getCreator());//通过Creator找到对应的user
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//将question与questionDTO共有的元素写入到questionDTO中
            questionDTO.setUser(user);//将user写入到questionDTO中去
            questionDTOS.add(questionDTO);//questionDTOS表中添加一个questionDTO
        }
        paginationDTO.setQuestions(questionDTOS);//将questionDTOS设置到paginationDTO中的questions表里去


        return paginationDTO;//在indexcontroller中调用
    }

    public PaginationDTO list(String AccountId, Integer page, Integer size) {//找所有自己发布的问题
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalcount=questionMapper.countByAccountId(AccountId);//总问题条数
        paginationDTO.setPagination(totalcount,page,size);//给paginationDTO里面除了question表以外的设置好 然后返回出去
        if (page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        //上面的代码写在前面能保证page不会溢出
        Integer offset=size*(page-1);//page页码 size每页个数 offset是显示的开始位置
        List<Question> questions=questionMapper.listByAccountId(AccountId,offset,size);//question列表 用于显示对应个数的问题的列表
        List<QuestionDTO> questionDTOS=new ArrayList<>();//dto列表用于存到前端去的列表 比上面question多了user信息

        for (Question question : questions) {
            User user=userMapper.findByAccountId(question.getCreator());//通过Creator找到对应的user
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//将question与questionDTO共有的元素写入到questionDTO中
            questionDTO.setUser(user);//将user写入到questionDTO中去
            questionDTOS.add(questionDTO);//questionDTOS表中添加一个questionDTO
        }
        paginationDTO.setQuestions(questionDTOS);//将questionDTOS设置到paginationDTO中的questions表里去
        return paginationDTO;//在indexcontroller中调用
    }
}
