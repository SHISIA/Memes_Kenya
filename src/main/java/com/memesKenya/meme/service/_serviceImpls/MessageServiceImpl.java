package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.model.MessageModel;
import com.memesKenya.meme.repository.MemerRepo;
import com.memesKenya.meme.repository.MessageRepo;
import com.memesKenya.meme.service._service.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepo repo;
    @Autowired
    private MemerRepo memerRepo;

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

    @Override
    public Optional<List<Message>> getMessageBySenderAndReceiver(UUID sender, UUID receiver) {
        Memer memberSender=memerRepo.findById(sender).get();
        Memer memberReceiver=memerRepo.findById(receiver).get();
        return Optional.ofNullable(repo.getMessages(memberSender,memberReceiver));
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
