package app.spring.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.spring.security.model.MyUser;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(username));

		MyUser user = mongoTemplate.findOne(query, MyUser.class);

		if (user != null) {
			return User.builder().username(user.getName()).password(user.getPassword()).roles(getRoles(user)).build();
		} else {
			throw new UsernameNotFoundException(username);
		}

	}

	private String[] getRoles(MyUser user) {
		if (user == null) {
			return new String[] { "USER" };
		}
		return user.getRole().split(",");
	}

}
