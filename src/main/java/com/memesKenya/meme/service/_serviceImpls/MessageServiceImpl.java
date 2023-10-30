package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.messaging.SocketMessage;
import com.memesKenya.meme.model.MessageModel;
import com.memesKenya.meme.repository.MemerRepo;
import com.memesKenya.meme.repository.MessageRepo;
import com.memesKenya.meme.repository.SocketMessageRepo;
import com.memesKenya.meme.service._service.MessageService;
import com.memesKenya.meme.service._service.SocketMessageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService, SocketMessageService {
    @Autowired
    private MessageRepo repo;
    @Autowired
    private MemerRepo memerRepo;

    @Autowired
    private SocketMessageRepo socketMessageRepo;

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
        repo.save(message_);
//        repo.updateSenderAndReceiver(sender.getUserId(),recipient.getUserId(),message_.getMessageId());
        return "Message Sent";
    }

    //getMessageBySender - For SocketMessage service
    @Override
    public List<SocketMessage> getMessagesBySenderAndReceiver(UUID sender, UUID receiver) {
        return socketMessageRepo.getMessagesBySenderAndReceiver(sender,receiver);
    }

    //Send - For socketMessage service
    @Override
    public SocketMessage sendMessage(SocketMessage message) {
        socketMessageRepo.save(message);
        return message;
    }

    //Delete - For socketMessage service
    @Override
    public boolean deleteSocketMessage(UUID sender, UUID receiver) {
        socketMessageRepo.deleteSocketMessage(sender,receiver);
        return false;
    }

    //getSocketMessage - For socketMessage service
    @Override
    public Optional<List<SocketMessage>> getSocketMessagesBySender(UUID senderId) {
        return socketMessageRepo.getSocketMessagesBySender(senderId);
    }

    @Override
    public Optional<List<Message>> getMessageBySenderAndReceiver(UUID sender, UUID receiver) {
        Memer memberSender=memerRepo.findById(sender).get();
        Memer memberReceiver=memerRepo.findById(receiver).get();
        return Optional.ofNullable(repo.getMessagesBySenderAndReceiver(memberSender,memberReceiver));
    }

    //they are the sender:they send
    //I am the recipient: I receive
    @Override
    public Optional<List<Message>> getMessagesBySender(UUID sender) {
        Memer memberUUID=memerRepo.findById(sender).get();
        return Optional.ofNullable(repo.findBySender(memberUUID));
    }

    @Transactional
    @Override
    public boolean deleteMessage(UUID sender,UUID receiver) {
        Memer memberSender=memerRepo.findById(sender).get();
        Memer memberReceiver=memerRepo.findById(receiver).get();
        repo.deleteMessage(memberSender, memberReceiver);
        return true;
    }

    @Override
    public List<Message> getMessageContaining(String content,String nickName) {
        UUID memerId=memerRepo.findByNickName(nickName).getUserId();
//        if (!(content.isBlank() && memerId.toString().isBlank()))
//        return repo.findByMessageContaining(content,memerId);
        return null;
    }
}
