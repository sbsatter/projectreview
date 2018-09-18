package com.sbsatter.projectreview.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by sbsatter on 9/18/18.
 */
@NoArgsConstructor
@Data
public class User {
	public Integer id;
	public String username;
	public String name;
	public String password;
	public String phone;
	public String role;
}
