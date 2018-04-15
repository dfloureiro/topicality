package com.dfl.topicality.injection.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

@Scope
@Singleton
@Retention(RetentionPolicy.CLASS)
public @interface TopicalityApplicationScope {
}
