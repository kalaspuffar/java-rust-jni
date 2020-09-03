extern crate jni;

use jni::sys::jbyteArray;
use jni::objects::JClass;
use jni::JNIEnv;

#[no_mangle]
#[allow(non_snake_case)]
pub extern fn Java_JavaRustFFI_hello(_env: JNIEnv, _class: JClass) {
    println!("hello from rust");
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_JavaRustFFI_optimize_1from_1memory(env: JNIEnv, _class: JClass, a: jbyteArray) -> jbyteArray {
    let converted : &[u8] = &JNIEnv::convert_byte_array(&env, a).unwrap();
    return JNIEnv::byte_array_from_slice(&env, converted).unwrap();
}