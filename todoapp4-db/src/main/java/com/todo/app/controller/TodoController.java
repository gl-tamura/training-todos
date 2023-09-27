package com.todo.app.controller;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.app.entity.Todo;
import com.todo.app.mapper.TodoMapper;

import lombok.RequiredArgsConstructor;

/**
 * TODOコントローラー
 */
@RequiredArgsConstructor
@Controller
public class TodoController {
	// DBアクセス
    private final TodoMapper todoMapper;
    // メッセージ
	private String message = "";
	
	/**
	 * indexのメソッド　画面表示・データの初期読み込み
	 * 主にクラスとクラスパスを紐づけ クラスへのリクエストのURLは　http://localhost:8080/
	 * 
	 * @param model
	 * @return index.htmlを返す。
	 * @throws SQLException 
	 */
	@GetMapping (value="/")
    public  String index(Model model) throws SQLException {
		model.addAttribute("message",message);
		// 画面へ渡すデータをmodelに設定
        model.addAttribute("todos",todoMapper.selectAll());
        // index.htmlを表示する
        return "index";
	}   
	
	/**
	 * TODO追加処理
	 * @param todo
	 * @return
	 */
    @PostMapping (value="/add")
    public String add(Todo todo) {
    	message = "";
    	if (todo.getTitle().length() > 0) {
        	if(todoMapper.add(todo)== false) {
        		message = "追加処理に失敗しました。";
        	}
    	}
    	return "redirect:/";
    }
    
    /**
     * リストの変更
     * @param todo　＝　Todo型のtodoという配列
     * @return　ｓｔｒ型でvalueへ返す　＝index.htmlの表示
     */
    @PostMapping(value="/change")
    public String change(Todo todo) { 
    	message = "";
    	if(todoMapper.update(todo)== false) {
    		message = "更新処理に失敗しました。";
    	}
    	return "redirect:/";
    }

    /**
     * リストの削除
     * @param todo＝　Todo型のtodoという配列
     * @return　ｓｔｒ型でvalueへ返す　＝index.htmlの表示
     */
    @PostMapping (value="/delete")
    public String delete(Todo todo) {
    	message = "";
    	if(todoMapper.delete(todo)== false) {
    		message = "削除処理に失敗しました。";
    	}
    	return "redirect:/";
    }
	    
}
