package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.model.MessageModel;
import com.memesKenya.meme.repository.MemerRepo;
import com.memesKenya.meme.repository.MessageRepo;
import com.memesKenya.meme.service._service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepo repo;
    @Autowired
    MemerRepo memerRepo;

    @Override
    public List<Message> getMessagesByNickName(String nickName) {
        Memer memer=memerRepo.findByNickName(nickName);
//        return repo.findBySender(memer.getUserId());
        return null;
    }

    @Override
    public String sendMessage(MessageModel message) {
        Memer recipient=memerRepo.findById(message.getRecipient()).get();
        Memer sender=memerRepo.findById(message.getSender()).get();
        Message message_=new Message(sender,recipient,message.getMessage_content(), Timestamp.from(Instant.now()));
//        repo.save(message_);
//        repo.updateSenderAndReceiver(sender.getUserId(),recipient.getUserId(),message_.getMessageId());
        return "Message Sent";
    }

    //they are the sender:they send
    //I am the recipient: I receive
    @Override
    public List<Message> getMessagesBySender(String nickName,String receiver) {
        UUID sender=memerRepo.findByNickName(nickName).getUserId();
        UUID receiverId=memerRepo.findByNickName(receiver).getUserId();

//        return repo.findBySender(sender,receiverId);
        return null;
    }

    @Override
    public List<Message> getMessageContaining(String content,String nickName) {
        UUID memerId=memerRepo.findByNickName(nickName).getUserId();
//        if (!(content.isBlank() && memerId.toString().isBlank()))
//        return repo.findByMessageContaining(content,memerId);
        return null;
    }
}
