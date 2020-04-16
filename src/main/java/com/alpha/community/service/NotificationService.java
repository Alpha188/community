package com.alpha.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.community.dto.NotificationDTO;
import com.alpha.community.enums.CustomizeErrorCodeEnum;
import com.alpha.community.enums.NotificationStatusEnum;
import com.alpha.community.enums.NotificationTypeEnum;
import com.alpha.community.exception.CustomizeErrorCode;
import com.alpha.community.exception.CustomizeException;
import com.alpha.community.mapper.NotificationMapper;
import com.alpha.community.model.Notification;
import com.alpha.community.model.NotificationExample;
import com.alpha.community.model.User;

@Service
public class NotificationService {
	@Autowired
	private NotificationMapper notificationMapper;

	public List<NotificationDTO> listByReceiver(Long receiver) {
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(receiver);
		notificationExample.setOrderByClause("status,gmt_create desc");
		List<Notification> notifications = notificationMapper.selectByExample(notificationExample);

		List<NotificationDTO> notificationDTOS = new ArrayList<>();
		if (notifications.size() == 0) {
			return notificationDTOS;
		}

		for (Notification notification : notifications) {
			NotificationDTO notificationDTO = new NotificationDTO();
			BeanUtils.copyProperties(notification, notificationDTO);
			notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
			notificationDTOS.add(notificationDTO);
		}
		return notificationDTOS;
	}
	
	public Long countUnread(Long userId) {
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(userId)
				.andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
		return notificationMapper.countByExample(notificationExample);
	}

	public NotificationDTO read(Long id, User user) {
		 Notification notification = notificationMapper.selectByPrimaryKey(id);
	        if (notification == null) {
	            throw new CustomizeException(CustomizeErrorCodeEnum.NOTIFICATION_NOT_FOUND);
	        }
	        if (notification.getReceiver()!=user.getId()) {
	            throw new CustomizeException(CustomizeErrorCodeEnum.READ_NOTIFICATION_FAIL);
	        }

	        notification.setStatus(NotificationStatusEnum.READ.getStatus());
	        notificationMapper.updateByPrimaryKey(notification);

	        NotificationDTO notificationDTO = new NotificationDTO();
	        BeanUtils.copyProperties(notification, notificationDTO);
	        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
	        return notificationDTO;
	}
}
