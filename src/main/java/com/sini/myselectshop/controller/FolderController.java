package com.sini.myselectshop.controller;

import com.sini.myselectshop.dto.FolderRequestDto;
import com.sini.myselectshop.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@PostMapping("/folders")
public void addFolders(@RequestBody FolderRequestDto folderRequestDto,
                       @AuthenticationPrincipal UserDetailsImpl userDetails) {

    List<String> folderNames = folderRequestDto.getFolderNames();

    folderService.addFolders(folderNames, userDetails.getUser());
}
