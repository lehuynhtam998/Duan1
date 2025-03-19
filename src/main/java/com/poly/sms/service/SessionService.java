package com.poly.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.sms.entity.Product;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
    @Autowired
    private HttpSession session;

    @SuppressWarnings("unchecked")
    public <T> T get(String name, Class<T> type) {
        Object attribute = session.getAttribute(name);
        if (attribute != null && type.isInstance(attribute)) {
            return (T) attribute;
        }
        return null;
    }

    public void set(String name, Object value) {
        session.setAttribute(name, value);
    }

    public void remove(String name) {
        session.removeAttribute(name);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getList(String name) {
        Object attribute = session.getAttribute(name);
        if (attribute != null && attribute instanceof List<?>) {
            return (List<Integer>) attribute;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Product> getListProduct(String name) {
        Object attribute = session.getAttribute(name);
        if (attribute != null && attribute instanceof List<?>) {
            return (List<Product>) attribute;
        }
        return null;
    }
}
