package cn.itdeer.modules.admin.system.web;

import cn.itdeer.common.base.BaseController;
import cn.itdeer.common.base.BaseMessage;
import cn.itdeer.common.exception.ValidateException;
import cn.itdeer.modules.admin.system.entity.Dict;
import cn.itdeer.modules.admin.system.entity.Logs;
import cn.itdeer.modules.admin.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述：系统-字典-Controller层
 * 创建人：Itdeer
 * 创建时间：2017/8/19 16:23
 */

@Controller
@RequestMapping("/admin/system/dict")
public class DictController extends BaseController{

    @Autowired
    private DictService dictService;

    /**
     * 分页-全部-查询
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,Model model){
        Page<Dict> pageList = dictService.findAll(page);
        model.addAttribute("pageList",pageList);

        List<String> list = dictService.type();
        model.addAttribute("list",list);

        model.addAttribute("url","/admin/system/dict/findAll");
        return "admin/system/dict_list";
    }

    /**
     * 按ID-查询
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/form/{id}",method = RequestMethod.GET)
    public String form(@PathVariable String id,Model model){
        if(id != null){
            Dict form = dictService.findById(id);
            model.addAttribute("form",form);
        }

        List<String> list = dictService.type();
        model.addAttribute("list",list);
        return "admin/system/dict_form";
    }

    /**
     * 添加键值
     * @param type
     * @param model
     * @return
     */
    @RequestMapping(value = "/addKey/{type}",method = RequestMethod.GET)
    public String addKey(@PathVariable String type,Model model){
        Dict form = new Dict();
        form.setType(type);
        model.addAttribute("form",form);
        return "admin/system/dict_form";
    }

    /**
     * 字典-保存
     * @param dict
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Dict dict,Model model,HttpServletRequest request){
        try {
            dictService.save(dict);
            addMessage(model,new BaseMessage("字典信息保存完成","执行成功！","success"));

            Page<Dict> pageList = dictService.findByType(0,dict.getType());
            model.addAttribute("pageList",pageList);

            model.addAttribute("url","/admin/system/dict/findByType?type=" + dict.getType() + "&");
            //addLogs(request,new Logs("info","系统日志","保存字典",null));
        } catch (ValidateException e) {
            model.addAttribute("form",dict);
            addMessage(model,new BaseMessage(e.getMessage(),"执行失败！","error"));
           // addLogs(request,new Logs("error","系统日志","保存字典",e.getMessage()));
            return "admin/system/dict_form";
        }

        return "admin/system/dict_list";
    }

    /**
     * 字典-删除-按ID
     * @param id
     * @param model
     * @param ra
     * @return
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public String delete(@PathVariable String id, Model model, RedirectAttributes ra,HttpServletRequest request){
        dictService.delete(id);
        addMessage(ra,new BaseMessage("字典信息删除完成","执行成功！","success"));
       // addLogs(request,new Logs("info","系统日志","删除字典",null));
        return "redirect:/admin/system/dict/findAll";
    }

    /**
     * 描述模糊查询
     * @param page
     * @param description
     * @param model
     * @return
     */
    @RequestMapping(value = "/desLike",method = RequestMethod.POST)
    public String desLike(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam String description, Model model){
        Page<Dict> pageList = dictService.desLike(page,"%" + description + "%");
        model.addAttribute("pageList",pageList);

        List<String> list = dictService.type();
        model.addAttribute("list",list);

        model.addAttribute("description",description);

        model.addAttribute("url","/admin/system/dict/desLike?description=" + description + "&");
        return "admin/system/dict_list";
    }


    /**
     * 按照类型查询
     * @param page
     * @param type
     * @param model
     * @return
     */
    @RequestMapping(value = "/findByType",method = RequestMethod.POST)
    public String findByType(@RequestParam(value = "page", defaultValue = "0") Integer page,@RequestParam String type, Model model){
        Page<Dict> pageList;
        if("all".equals(type)){
            pageList = dictService.findAll(page);
        }else{
            pageList = dictService.findByType(page,type);
        }

        model.addAttribute("pageList",pageList);

        List<String> list = dictService.type();
        model.addAttribute("list",list);

        model.addAttribute("type",type);

        model.addAttribute("url","/admin/system/dict/findByType?type=" + type + "&");
        return "admin/system/dict_list";
    }

}
